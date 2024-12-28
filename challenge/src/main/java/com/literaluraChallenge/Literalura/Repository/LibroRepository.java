package com.literaluraChallenge.Literalura.Repository;

import com.literaluraChallenge.Literalura.Model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    boolean existsByTitulo(String titulo);
}

