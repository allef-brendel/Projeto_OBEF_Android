package com.example.obef.Modelos;

import com.example.obef.Configuracao.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

public class Escola {
    String nome;
    String cidade;
    String estado;

    public Escola(String nome, String cidade, String estado) {
        this.nome = nome;
        this.cidade = cidade;
        this.estado = estado;
    }

    public String getNome() {
        return nome;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void salvar(){
        DatabaseReference databaseFirebase = ConfiguracaoFirebase.getReference();
        databaseFirebase.child("Escolas").child("1").setValue(this);
    }

}
