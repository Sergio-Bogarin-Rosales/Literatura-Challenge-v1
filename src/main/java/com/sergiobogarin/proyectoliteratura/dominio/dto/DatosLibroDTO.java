package com.sergiobogarin.proyectoliteratura.dominio.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) // notacion de jacksoon-core
public record DatosLibroDTO(
        @JsonAlias("title") String titulo, // notacion de jackson-core
        @JsonAlias("authors") List<DatosAutorDTO> autores,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("download_count") Integer numeroDescargas) {

}
