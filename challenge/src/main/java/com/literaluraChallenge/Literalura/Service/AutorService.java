package com.literaluraChallenge.Literalura.Service;

import com.literaluraChallenge.Literalura.DTO.AutorDTO;
import com.literaluraChallenge.Literalura.Model.Autor;
import com.literaluraChallenge.Literalura.Repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorService {
    @Autowired
    AutorRepository autorRepository;

    public Autor saveAutor(AutorDTO autorDTO) {
        if (autorRepository.existsByNombre(autorDTO.nombre())) {
            throw new IllegalArgumentException("Un autor con el mismo nombre ya existe.");
        }

        Autor autor = new Autor();
        autor.setNombre(autorDTO.nombre());
        autor.setFechaNacimiento(autorDTO.fechaNacimiento());
        autor.setFechaFallecimiento(autorDTO.fechaFallecimiento());
        autor.setLibros(autorDTO.libros());

        return autorRepository.save(autor);
    }
}
