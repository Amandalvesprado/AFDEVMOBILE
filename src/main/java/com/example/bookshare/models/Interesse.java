package com.example.bookshare.models;

public class Interesse {
    private String livroId;
    private String userIdInteressado;

    public Interesse() {
        // Firestore exige construtor vazio
    }

    public Interesse(String livroId, String userIdInteressado) {
        this.livroId = livroId;
        this.userIdInteressado = userIdInteressado;
    }

    public String getLivroId() { return livroId; }
    public void setLivroId(String livroId) { this.livroId = livroId; }

    public String getUserIdInteressado() { return userIdInteressado; }
    public void setUserIdInteressado(String userIdInteressado) { this.userIdInteressado = userIdInteressado; }
}
