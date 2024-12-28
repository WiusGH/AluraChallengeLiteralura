package com.literaluraChallenge.Literalura.Request;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Service
public class Request {
    /**
     * Obtiene el primer resulta al llamar la API Gutendex y pasarle una lista de una o más palabras
     *
     * @param query Una lista de palabras a buscar
     * @return Retorna un objeto en formato JSON con la información del libro encontrado
     * @throws IOException          Si un error "I/O" ocurre
     * @throws InterruptedException Si la operación es interrumpida
     */
    public Map<String, Object> fetchFirstResult(List<String> query) throws IOException, InterruptedException {
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

            String title = firstItem.get("title").asText();
            String author = firstItem.get("authors").get(0).get("name").asText();
            String language = firstItem.get("languages").get(0).asText();
            int downloadCount = firstItem.get("download_count").asInt();

            String birthYear = null;
            String deathYear = null;
            JsonNode authorNode = firstItem.get("authors").get(0);
            if (authorNode.has("birth_year")) {
                birthYear = authorNode.get("birth_year").asText();
            }
            if (authorNode.has("death_year")) {
                deathYear = authorNode.get("death_year").asText();
            }

            Map<String, Object> bookData = new HashMap<>();
            bookData.put("title", title);
            bookData.put("author", author);
            bookData.put("language", language);
            bookData.put("download_count", downloadCount);
            bookData.put("birth_year", birthYear);
            bookData.put("death_year", deathYear);

            return bookData;
        } else {
            throw new IOException("No book found for the given search terms.");
        }
    }
}
