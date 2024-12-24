package com.literaluraChallenge.Literalura.DTO;

import com.literaluraChallenge.Literalura.Model.Libro;

import java.util.List;

public record AutorDTO(
        long id,
        String nombre,
        String fechaNacimiento,
        String fechaFallecimiento,
        List<Libro>libros
) {
}
