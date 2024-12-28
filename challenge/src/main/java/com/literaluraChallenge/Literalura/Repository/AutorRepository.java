package com.literaluraChallenge.Literalura.Repository;

import com.literaluraChallenge.Literalura.Model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    boolean existsByNombre(String nombre);
}
