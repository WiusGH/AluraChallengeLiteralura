package com.literaluraChallenge.Literalura.Service;

import com.literaluraChallenge.Literalura.DTO.AutorDTO;
import com.literaluraChallenge.Literalura.Model.Autor;
import com.literaluraChallenge.Literalura.Repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {
    @Autowired
    AutorRepository autorRepository;

    public void saveAutor(AutorDTO autorDTO) {
        Autor autor;
        if (autorRepository.existsByNombre(autorDTO.nombre())) {
            autor = (Autor) autorRepository.findByNombre(autorDTO.nombre()).orElseThrow(() ->
                    new RuntimeException("El autor existe pero no pude ser obtenido.")
            );
            String existingBooks = autor.getLibros();
            String newBook = autorDTO.libros();

            if (existingBooks == null || existingBooks.isEmpty()) {
                autor.setLibros(newBook);
            } else if (!existingBooks.contains(newBook)) {
                autor.setLibros(existingBooks + " - " + newBook);
            } else {
                System.out.println("*** Este libro ya está asociado a un autor en la base de datos ***\n");
            }

        } else {
            autor = new Autor();
            autor.setNombre(autorDTO.nombre());
            autor.setFechaNacimiento(autorDTO.fechaNacimiento());
            autor.setFechaFallecimiento(autorDTO.fechaFallecimiento());
            autor.setLibros(autorDTO.libros());
        }
        autorRepository.save(autor);
    }

    public List<Autor> findAllAutores() {
        return autorRepository.findAll();
    }

    public List<Autor> findAutoresAliveInYear(int year) {
        return autorRepository.findAll().stream()
                .filter(autor -> Integer.parseInt(autor.getFechaNacimiento()) <= year &&
                        (autor.getFechaFallecimiento() == null || Integer.parseInt(autor.getFechaFallecimiento()) > year))
                .toList();
    }
}
