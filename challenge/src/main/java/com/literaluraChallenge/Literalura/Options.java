package com.literaluraChallenge.Literalura;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.literaluraChallenge.Literalura.Request.Request;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Options {
    Scanner scanner = new Scanner(System.in);
    public String optionOne() {
        Request request = new Request();
        System.out.println("Introduce el título del libro o parte del título:");
        System.out.println("(Solo se mostrarán libros que no estén bajo protección de derechos de autor).");
        String words = scanner.nextLine();
        System.out.println("Buscando libro...");
        List<String> queryWords = List.of(words.split(" "));
        try {
            Map<String, Object> response = request.fetchFirstResult(queryWords);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(response);
            System.out.println("******************************");
            System.out.println("Libro encontrado:");
            System.out.println("Título: " + response.get("title"));
            System.out.println("Autor: " + response.get("author"));
            System.out.println("Idioma: " + response.get("language"));
            System.out.println("Número de Descargas: " + response.get("download_count"));
            System.out.println("******************************\n");
            return jsonResponse;
        } catch (Exception e) {
            System.out.println("Libro no encontrado. " + e.getMessage());
            return null;
        }
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
