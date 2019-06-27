package com.example.obef.Modelos;

import java.util.ArrayList;
import java.util.List;


public class Alternativa {

    boolean status;
    String texto;
    String justificativa;

    public Alternativa(boolean status, String texto, String justificativa) {
        this.status = status;
        this.texto = texto;
        this.justificativa = justificativa;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }



    public Alternativa() {
    }

    public String getSaveString(){
        String s="";
        s+=status+"|";
        s+=texto+"|";
        s+=justificativa+"|";
        return s;
    }
    public List<String> getArgumentos(){
        List<String> s=new ArrayList<String>();
        s.add(this.justificativa);
        s.add(this.texto);
        s.add(""+this.status);
        return s;
    }
}