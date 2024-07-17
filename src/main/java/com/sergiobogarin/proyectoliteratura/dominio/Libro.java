package com.sergiobogarin.proyectoliteratura.dominio;

import jakarta.persistence.Table;

import com.sergiobogarin.proyectoliteratura.dominio.dto.DatosLibroDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity // se establece la clase libro como una entidad de la base de datos
@Table(name = "libros") // y se le da el nombre de libros
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    private String idioma;
    private int numeroDescargas;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL) // Asegura que el autor se guarde automáticamente
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Libro(DatosLibroDTO datosLibro) {
        this.titulo = datosLibro.titulo();
        this.idioma = datosLibro.idiomas().get(0);
        this.numeroDescargas = datosLibro.numeroDescargas();
        this.autor = datosLibro.autores().stream().map(Autor::new).toList().get(0);

    }

    public void addAutor(Autor autor) {
        autor.getLibros().add(this);
    }

    @Override
    public String toString() {
        return "-----LIBRO-----\n" +
                "Titulo: " + titulo + "\n" +
                "Autor: " + autor.getNombre() + "\n" +
                "Idioma: " + idioma + "\n" +
                "Numero de descargas: " + numeroDescargas + "\n" +
                "--------------";

    }

}
