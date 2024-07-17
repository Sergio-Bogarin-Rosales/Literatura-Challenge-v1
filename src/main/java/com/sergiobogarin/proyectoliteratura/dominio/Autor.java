package com.sergiobogarin.proyectoliteratura.dominio;

import com.sergiobogarin.proyectoliteratura.dominio.dto.DatosAutorDTO;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private int anioNacimiento;
    private int anioFallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros = new ArrayList<>();

    public Autor(DatosAutorDTO datosAutorDTO) {
        this.nombre = datosAutorDTO.nombre();
        this.anioNacimiento = datosAutorDTO.anioNacimiento();
        this.anioFallecimiento = datosAutorDTO.anioFallecimiento();
    }

    @Override
    public String toString() {
        return "\n---------- Autor: " + nombre + "----------\n" + 
                "Fecha de nacimiento: " + anioNacimiento + "\n" +
                "Fecha de fallecimiento: " + anioFallecimiento + "\n" +
                "Libros: " + libros.stream().map(Libro::getTitulo).toList() + "\n";

    }

    
}
