package com.example.obef.Modelos;


import com.example.obef.Configuracao.ConfiguracaoFirebase;
import com.example.obef.Gerenciamento.Gravador;
import com.google.firebase.database.DatabaseReference;

public class Aluno {

    String id;
    String nome;
    String login;
    String senha;
    String email;
    String sexo;
    String serie;
    int idEscola;

    public Aluno() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public String getEmail() {
        return email;
    }

    public String getSexo() {
        return sexo;
    }

    public String getSerie() {
        return serie;
    }

    public int getIdEscola() {
        return idEscola;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public void setIdEscola(int idEscola) {
        this.idEscola = idEscola;
    }

    public Aluno(String nome, String login, String senha, String email, String sexo, String serie, int idEscola){
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.email = email;
        this.sexo = sexo;
        this.serie = serie;
        this.idEscola = idEscola;
    }

    public void salvar() {
        DatabaseReference databaseFirebase = ConfiguracaoFirebase.getReference();
        databaseFirebase.child("Alunos").child(getId()).setValue(this);
        databaseFirebase.child("Acertos").child(getId()).child("pontos").setValue(0);
        Gravador gravador=new Gravador();
        databaseFirebase.child("Acertos").child(getId()).child("ultimoDiaLogado").setValue("0");
    }

}
