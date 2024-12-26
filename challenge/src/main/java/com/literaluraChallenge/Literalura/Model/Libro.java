package com.literaluraChallenge.Literalura.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libro")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String titulo;
    @ElementCollection
    @CollectionTable(name = "libro_idiomas", joinColumns = @JoinColumn(name = "libro_id"))
    @Column(name = "idioma")
    private List<String> idioma;
    private String numeroDescargas;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Autor autor;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public List<String> getIdioma() {
        return idioma;
    }

    public void setIdioma(List<String> idioma) {
        this.idioma = idioma;
    }

    public String getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(String numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }
}
