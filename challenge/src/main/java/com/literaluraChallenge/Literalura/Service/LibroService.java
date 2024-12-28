package com.literaluraChallenge.Literalura.Service;

import com.literaluraChallenge.Literalura.DTO.LibroDTO;
import com.literaluraChallenge.Literalura.Model.Libro;
import com.literaluraChallenge.Literalura.Repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroService {
    @Autowired
    private LibroRepository libroRepository;

    public Libro saveLibro(LibroDTO libroDTO) {
        if (libroRepository.existsByTitulo(libroDTO.titulo())) {
            throw new IllegalArgumentException("Un libro con el mismo título ya existe.");
        }

        Libro libro = new Libro();
        libro.setTitulo(libroDTO.titulo());
        libro.setAutor(libroDTO.autor());
        libro.setIdioma((libroDTO.idioma()));
        libro.setNumeroDescargas(libroDTO.numeroDescargas());

        return libroRepository.save(libro);
    }

}
