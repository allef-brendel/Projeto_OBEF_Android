package com.example.obef.Modelos;

import com.example.obef.Configuracao.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

public class Adm {

    private String nome;
    private String email;
    private String id;
    private String senha;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Adm() {
    }

    public void salvar() {
        DatabaseReference databaseFirebase = ConfiguracaoFirebase.getReference();
        databaseFirebase.child("Adm").child(getId()).setValue(this);
    }
}
