package com.literaluraChallenge.Literalura;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.literaluraChallenge.Literalura.DTO.AutorDTO;
import com.literaluraChallenge.Literalura.DTO.LibroDTO;
import com.literaluraChallenge.Literalura.Model.Autor;
import com.literaluraChallenge.Literalura.Model.Libro;
import com.literaluraChallenge.Literalura.Request.Request;
import com.literaluraChallenge.Literalura.Service.AutorService;
import com.literaluraChallenge.Literalura.Service.LibroService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
public class Options {
    private final LibroService libroService;
    private final AutorService autorService;
    Scanner scanner = new Scanner(System.in);

    public Options(LibroService libroService, AutorService autorService) {
        this.libroService = libroService;
        this.autorService = autorService;
    }

    public void optionOne() {
        Request request = new Request();
        String option;

        do {
            System.out.println("Introduce el título del libro o parte del título:");
            System.out.println("(Solo se mostrarán libros que no estén bajo protección de derechos de autor).");
            String words = scanner.nextLine().toLowerCase();
            if (words.equals("salir")) {
                System.exit(0);
            }
            System.out.println("Buscando libro...");
            List<String> queryWords = List.of(words.split(" "));

            try {
                Map<String, Object> response = request.fetchFirstResult(queryWords);
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonResponse = objectMapper.writeValueAsString(response);

                LibroDTO libroDTO = objectMapper.readValue(jsonResponse, LibroDTO.class);
                AutorDTO autorDTO = objectMapper.readValue(jsonResponse, AutorDTO.class);

                System.out.println("******************************");
                System.out.println("Libro encontrado:");
                System.out.println("Título: " + response.get("title"));
                System.out.println("Autor: " + response.get("author"));
                System.out.println("Idioma: " + response.get("language"));
                System.out.println("Número de Descargas: " + response.get("download_count"));
                System.out.println("******************************\n");

                libroService.saveLibro(libroDTO);
                autorService.saveAutor(autorDTO);
            } catch (JsonProcessingException e) {
                System.out.println("Error al procesar la respuesta: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Libro no encontrado. " + e.getMessage());
            }
            System.out.println("""
                ¿Deseas buscar otro libro? Elige una opción:\s

                1- Buscar otro libro.\s
                2- Volver al menú principal.\s
                0- Salir.""");

            option = scanner.nextLine().toLowerCase();

            switch (option) {
                case "1":
                    break;
                case "2":
                    return;
                case "0":
                case "salir":
                    System.exit(0);
                default:
                    System.out.println("Opción inválida.");
            }
        } while (true);
    }

    public void optionTwo() {
        List<Libro> libros = libroService.findAllLibros();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en la base de datos.");
        } else {
            System.out.println("******************************");
            System.out.println("Lista de libros registrados:");
            libros.forEach(libro -> {
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Autor: " + libro.getAutor());
                System.out.println("Idioma: " + libro.getIdioma());
                System.out.println("Número de Descargas: " + libro.getNumeroDescargas());
                System.out.println("-----------------------------");
            });
            System.out.println("******************************\n");
        }
    }

    public void optionThree() {
        List<Autor> autores = autorService.findAllAutores();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados en la base de datos.");
        } else {
            System.out.println("******************************");
            System.out.println("Lista de autores registrados:");
            autores.forEach(autor -> {
                System.out.println("Nombre: " + autor.getNombre());
                System.out.println("Fecha de Nacimiento: " + autor.getFechaNacimiento());
                System.out.println("Fecha de Fallecimiento: " + autor.getFechaFallecimiento());
                System.out.println("Libros: " + autor.getLibros());
                System.out.println("-----------------------------");
            });
            System.out.println("******************************\n");
        }
    }

    public String optionFour() {
            return null;
        }
        public String optionFive() {
            return null;
        }

}
