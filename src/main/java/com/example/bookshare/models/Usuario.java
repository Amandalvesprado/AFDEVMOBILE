package com.example.bookshare.models;


public class Usuario {
    private String nome;
    private String email;
    private String userId;

    public Usuario() {
        // Firestore exige construtor vazio
    }

    public Usuario(String nome, String email, String userId) {
        this.nome = nome;
        this.email = email;
        this.userId = userId;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
}
