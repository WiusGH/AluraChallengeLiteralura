package com.literaluraChallenge.Literalura.DTO;

public record LibroDTO(
        long id,
        String titulo,
        String autor,
        String anio,
        String idioma,
        String numeroDescargas
) {
}
