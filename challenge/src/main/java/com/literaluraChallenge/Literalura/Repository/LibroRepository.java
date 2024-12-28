package com.literaluraChallenge.Literalura.Repository;

import com.literaluraChallenge.Literalura.Model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    boolean existsByTitulo(String titulo);

    List<Libro> findByIdioma(String idioma);
}

