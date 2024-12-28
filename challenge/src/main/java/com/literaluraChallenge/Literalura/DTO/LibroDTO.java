package com.literaluraChallenge.Literalura.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LibroDTO(
        long id,
        @JsonAlias("title") String titulo,
        @JsonAlias("author") String autor,
        @JsonAlias("language") String idioma,
        @JsonAlias("download_count") String numeroDescargas
) {
}
