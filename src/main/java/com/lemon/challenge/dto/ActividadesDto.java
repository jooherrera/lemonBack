package com.lemon.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ActividadesDto {

    private long cuit;
    private long cuitRepresentado;
    private String domicilio;
    private String tipoTelefono;
    private int telefono;
    private int codigoActividad;
    private String descripcionActividad;
    private String descripcionCaracter;
    private String descripcionCondicion;

}
