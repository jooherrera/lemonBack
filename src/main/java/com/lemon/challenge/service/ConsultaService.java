package com.lemon.challenge.service;

import com.lemon.challenge.dto.ActividadesDto;
import com.lemon.challenge.repository.ConsultaRepository;
import java.sql.SQLException;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsultaService {

    @Autowired
    ConsultaRepository repo;

    public ArrayList<ActividadesDto> generarConsultaActividades() throws SQLException {
        try {
            return repo.consultarActividades();
        } catch (Exception e) {
            repo.closeConnection();
            repo.reconnect();
            return repo.consultarActividades();
        }

    }

}
