package com.sergiobogarin.proyectoliteratura.dominio.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


// primer catcha de respuesta
@JsonIgnoreProperties(ignoreUnknown = true)
public record RespuestaDTO(
    @JsonAlias("results") List<DatosLibroDTO> libros 
) {

}
