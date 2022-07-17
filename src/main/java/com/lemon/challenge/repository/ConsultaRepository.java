package com.lemon.challenge.repository;

import com.lemon.challenge.dto.ActividadesDto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

@Repository
public class ConsultaRepository {

    private String uri;
    private String user;
    private String password;
    private Connection conn;

    @Autowired
    public ConsultaRepository(Environment env) throws SQLException {
        uri = env.getProperty("sql.uri");
        user = env.getProperty("sql.user");
        password = env.getProperty("sql.password");
        conectar(uri, user, password);
    }

    private void conectar(String uri, String user, String password) throws SQLException {
        try {
            if (uri.equals("") || user.equals("") || password.equals("")) {
                throw new SQLException("No se puede conectar a la base de datos. Faltan credenciales");
            }
            conn = DriverManager.getConnection(uri, user, password);
        } catch (SQLException e) {
            System.out.println("No se pudo conectar a la base de datos");
        }

    }

    public ArrayList<ActividadesDto> consultarActividades() throws SQLException {
        String query = "SELECT d.cuit, d.cuit_representado,d.domicilio,d.tipo_telefono, d.telefono, rde8000.CODIGO, rde8000.DESCRIPCION, rde8001.caracter, rde8002.condicion FROM RDE0000 as d \n"
                + "INNER JOIN RDE0002 as rde2 ON d.id = rde2.operacion_id\n"
                + "INNER JOIN RDE8000 as rde8000 ON rde2.actividad_id = rde8000.id \n"
                + "INNER JOIN RDE0001 as rde0001 ON rde0001.operacion_id = d.id\n"
                + "INNER JOIN RDE8001 as rde8001 ON rde8001.id = rde0001.caracter_id \n"
                + "INNER JOIN RDE8002 as rde8002 ON d.condicion_id = rde8002.id\n"
                + "ORDER BY d.fecha_operacion";
        ArrayList<ActividadesDto> list = new ArrayList();

        Statement stmt = conn.createStatement();
        ResultSet res = stmt.executeQuery(query);

        while (res.next()) {
            ActividadesDto consulta = new ActividadesDto();

            consulta.setCuit(res.getLong("cuit"));
            consulta.setCuitRepresentado(res.getLong("cuit_representado"));
            consulta.setDomicilio(res.getString("domicilio"));
            consulta.setTipoTelefono(res.getString("tipo_telefono"));
            consulta.setTelefono(res.getInt("telefono"));
            consulta.setCodigoActividad(res.getInt("CODIGO"));
            consulta.setDescripcionActividad(res.getString("Descripcion"));
            consulta.setDescripcionCaracter(res.getString("caracter"));
            consulta.setDescripcionCondicion(res.getString("condicion"));
            list.add(consulta);
        }

        stmt.close();
        return list;

    }

    public void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
            conn = null;
            System.out.println("Conexion cerrada");
        }
    }

    public void reconnect() throws SQLException {
        System.out.println("Intentando crear nueva conexion...");
        conn = DriverManager.getConnection(uri,user,password);
        System.out.println("Reconectado exitosamente.");
    }
}
