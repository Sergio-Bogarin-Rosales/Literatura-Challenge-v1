package com.sergiobogarin.proyectoliteratura.persistencia;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sergiobogarin.proyectoliteratura.dominio.Autor;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    Autor findByNombre(String nombre);

    @Query("select a from Autor a where a.anioNacimiento <= :anio and a.anioFallecimiento >= :anio")
    List<Autor> buscarAutoresVivosPorAnio(int anio);

}
