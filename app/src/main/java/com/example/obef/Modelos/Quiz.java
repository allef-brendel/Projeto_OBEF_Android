package com.example.obef.Modelos;

import com.example.obef.Gerenciamento.GerenciadorDeQuestoes;

import java.util.ArrayList;
import java.util.List;

public class Quiz {
    private List<Questao> desafio;
    private GerenciadorDeQuestoes gerenciadorDeQuestoes;
    private Questao atual;
    private int pontuacao;
    private boolean dica;

    private List<Integer> respondidos;
    public Quiz(){
        desafio=new ArrayList<Questao>(10);
        gerenciadorDeQuestoes=new GerenciadorDeQuestoes();
        pontuacao=0;
        this.respondidos=new ArrayList<Integer>();
        dica=false;
    }

    public String getEnunciado() {
        return atual.getEnunciado();
    }

    public String usarDica() {
        dica=true;
        return atual.getDica();
    }

    public void adicionarQuestoesDesafio() {
        gerenciadorDeQuestoes.getDesafio();
    }

    public void responderDesafio(int numDesafio) {
        this.atual=desafio.get(numDesafio);
        respondidos.add(numDesafio);
        this.dica=false;
    }

    private void acertouQuestaoComSemDica() {
        if(dica) pontuacao+=7;
        else pontuacao+=10;
    }

    public int getPontuacao(){
        return this.pontuacao;
    }
}