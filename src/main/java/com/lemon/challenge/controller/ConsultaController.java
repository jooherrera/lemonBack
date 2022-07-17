
package com.lemon.challenge.controller;

import com.lemon.challenge.service.ConsultaService;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1")
public class ConsultaController {
    
    @Autowired
    ConsultaService service;
    
    @GetMapping("/actividades")
    public ResponseEntity<Object> generarConsultaActividades(){
        try {
            return new ResponseEntity<>(service.generarConsultaActividades(),HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>("No hay conexion con la base de datos.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
