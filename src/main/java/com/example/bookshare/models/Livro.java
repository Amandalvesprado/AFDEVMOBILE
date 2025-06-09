package com.example.bookshare.models;

public class Livro {
    private String titulo;
    private String autor;
    private String genero;
    private String userId;

    public Livro() {
        // Firestore exige construtor vazio
    }

    public Livro(String titulo, String autor, String genero, String userId) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.userId = userId;
    }

    // Getters e Setters
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
}
