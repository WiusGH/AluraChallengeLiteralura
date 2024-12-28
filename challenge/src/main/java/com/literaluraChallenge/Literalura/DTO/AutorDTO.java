package com.literaluraChallenge.Literalura.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.literaluraChallenge.Literalura.Model.Libro;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AutorDTO(
        long id,
        @JsonAlias("author") String nombre,
        @JsonAlias("birth_year") String fechaNacimiento,
        @JsonAlias("death_year") String fechaFallecimiento,
        @JsonAlias("title") String libros
) {
}