package com.literaluraChallenge.Literalura.DTO;

import java.util.List;

public record LibroDTO(
        long id,
        String titulo,
        String autor,
        List<String> idioma,
        String numeroDescargas
) {
}
