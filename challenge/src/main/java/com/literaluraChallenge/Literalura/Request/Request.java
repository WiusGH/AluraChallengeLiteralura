package com.literaluraChallenge.Literalura.Request;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.literaluraChallenge.Literalura.DTO.LibroDTO;
import com.literaluraChallenge.Literalura.Model.Autor;
import com.literaluraChallenge.Literalura.Model.Libro;
import com.literaluraChallenge.Literalura.Repository.AutorRepository;
import com.literaluraChallenge.Literalura.Repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class Request {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    public Request(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    /**
     * Obtiene el primer resulta al llamar la API Gutendex y pasarle una lista de una o más palabras
     *
     * @param query Una lista de palabras a buscar
     * @return Retorna un objeto en formato JSON con la información del libro encontrado
     * @throws IOException          Si un error "I/O" ocurre
     * @throws InterruptedException Si la operación es interrumpida
     */
    public LibroDTO fetchFirstResult(List<String> query) throws IOException, InterruptedException {
        String queryString = String.join("%20", query);
        String url = "https://gutendex.com/books?search=" + queryString;

        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Error al obtener datos: HTTP " + response.statusCode());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.body());

        JsonNode results = rootNode.get("results");
        if (results != null && results.isArray() && !results.isEmpty()) {
            JsonNode firstItem = results.get(0);

            String titulo = firstItem.get("title").asText();
            String autorNombre = firstItem.get("authors").get(0).get("name").asText();
            List<String> idiomas = new ArrayList<>();
            if (firstItem.get("languages") != null && firstItem.get("languages").isArray()) {
                for (JsonNode langNode : firstItem.get("languages")) {
                    idiomas.add(langNode.asText());
                }
            }
            String numeroDescargas = firstItem.get("download_count") != null
                    ? firstItem.get("download_count").asText()
                    : "N/A";

            // Check if the book already exists
            Optional<Libro> existingLibro = libroRepository.findByTitulo(titulo);
            if (existingLibro.isPresent()) {
                return new LibroDTO(existingLibro.get().getId(), titulo, autorNombre, idiomas, numeroDescargas);
            }

            // Check if the author exists
            Autor autor = autorRepository.findByNombre(autorNombre).orElseGet(() -> {
                Autor newAutor = new Autor();
                newAutor.setNombre(autorNombre);
                autorRepository.save(newAutor);
                return newAutor;
            });

            // Create and save the new book
            Libro libro = new Libro();
            libro.setTitulo(titulo);
            libro.setAutor(autor);
            libro.setIdioma(idiomas);
            libro.setNumeroDescargas(numeroDescargas);
            libroRepository.save(libro);

            // Add the book to the author's list
            List<Libro> autorLibros = autor.getLibros();
            if (autorLibros == null) {
                autorLibros = new ArrayList<>();
            }
            autorLibros.add(libro);
            autor.setLibros(autorLibros);
            autorRepository.save(autor);

            return new LibroDTO(libro.getId(), titulo, autor.getNombre(), idiomas, numeroDescargas);
        } else {
            throw new IOException("No book found for the given search terms.");
        }
    }
}
