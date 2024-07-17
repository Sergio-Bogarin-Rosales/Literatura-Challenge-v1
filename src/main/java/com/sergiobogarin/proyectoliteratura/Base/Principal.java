package com.sergiobogarin.proyectoliteratura.Base;

import java.util.List;
import java.util.Scanner;

import com.sergiobogarin.proyectoliteratura.dominio.Autor;
import com.sergiobogarin.proyectoliteratura.dominio.Libro;
import com.sergiobogarin.proyectoliteratura.dominio.dto.DatosLibroDTO;
import com.sergiobogarin.proyectoliteratura.dominio.dto.RespuestaDTO;
import com.sergiobogarin.proyectoliteratura.persistencia.AutorRepository;
import com.sergiobogarin.proyectoliteratura.persistencia.LibroRepository;
import com.sergiobogarin.proyectoliteratura.service.ConsumoAPI;
import com.sergiobogarin.proyectoliteratura.service.ConvierteDatos;

public class Principal {
    private Scanner teclado = new Scanner(System.in);

    private final String URL_BASE = "http://gutendex.com/books/";
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();

    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository){
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }
    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    |--------- Administrador de libros ----------|
        
                        1 - Buscar libro por titulo
                        2 - Listar libros registrados
                        3 - Listar autores registrados
                        4 - Listar autores vivos en un determinado año
                        5 - Listar libros por idioma                                 
                        0 - Salir

                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1 -> buscarLibro();
                case 2 -> listarLibros();
                case 3 -> listarAutores();
                case 4 -> autoresVivosAnioDeterminado();
                case 5 -> listarLibrosPorIdioma();
                case 0 -> System.out.println("Cerrando la aplicación...");
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private DatosLibroDTO getDatosLibro() {

            System.out.println("Escribe el nombre del libro que deseas buscar: ");
            var nombreLibro = teclado.nextLine();
            var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
            //System.out.println(json);
            RespuestaDTO datos = conversor.obtenerDatos(json, RespuestaDTO.class);
            return datos.libros().get(0);

    }


    private void buscarLibro() {
        try{
            DatosLibroDTO datos = getDatosLibro();
            Libro libro = new Libro(datos);

            Autor autor = autorRepository.findByNombre(datos.autores().get(0).nombre());

            if(autor != null){
                libro.addAutor(autor);
                libro.setAutor(autor);
            }else {
                autorRepository.save(libro.getAutor());
            }

            libroRepository.save(libro);
            System.out.println(libro);
        }catch (IndexOutOfBoundsException e){
            System.out.println("El libro no se encontro");
        }catch (Exception e){
            System.out.println("No se pueden duplicar registros");

        }

    }

    private void listarLibros() {
        List<Libro> libros = libroRepository.findAll();
        libros.forEach(System.out::println);

    }

    private void listarAutores() {
        List<Autor> autores = autorRepository.findAll();
        autores.forEach(System.out::println);
    }

    private void autoresVivosAnioDeterminado() {
        System.out.println("Ingrese el año en el cual se desea buscar: ");
        var anio = teclado.nextInt();

        List<Autor> autoresVivos = autorRepository.buscarAutoresVivosPorAnio(anio);

        if(autoresVivos.isEmpty()){
            System.out.println("No se encontraron autores vivos en ese año");
        }else {
            autoresVivos.forEach(System.out::println);
        }

    }

    private void listarLibrosPorIdioma() {
        String opciones = """
                Establesca el idioma para buscar los libros:
                es - español
                en - ingles              
                """;
        System.out.println(opciones);
        var opcion = teclado.nextLine();
        if(opcion.equalsIgnoreCase("es") || opcion.equalsIgnoreCase("en")){
            List<Libro> librosIdioma = libroRepository.findByIdiomaIgnoreCase(opcion);
            if(librosIdioma.isEmpty()){
                System.out.println("Libros en el idioma seleccionado no han posido ser allados.");
            }else{
                librosIdioma.forEach(System.out::println);
            }
        }else{
            System.out.println("Opcion no valida");
        }

    }

}
