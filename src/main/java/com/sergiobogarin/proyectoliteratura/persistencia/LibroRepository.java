package com.sergiobogarin.proyectoliteratura.persistencia;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sergiobogarin.proyectoliteratura.dominio.Libro;

@Repository
public interface LibroRepository extends JpaRepository <Libro, Long>{
    
    List<Libro> findByIdiomaIgnoreCase(String idioma);

}