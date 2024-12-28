package com.literaluraChallenge.Literalura;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.literaluraChallenge.Literalura.DTO.AutorDTO;
import com.literaluraChallenge.Literalura.DTO.LibroDTO;
import com.literaluraChallenge.Literalura.Request.Request;
import com.literaluraChallenge.Literalura.Service.AutorService;
import com.literaluraChallenge.Literalura.Service.LibroService;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Options {
    Scanner scanner = new Scanner(System.in);
    public void optionOne(LibroService libroService, AutorService autorService) {
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

        public String optionTwo() {
            return null;
        }
        public String optionThree() {
            return null;
        }
        public String optionFour() {
            return null;
        }
        public String optionFive() {
            return null;
        }

}
