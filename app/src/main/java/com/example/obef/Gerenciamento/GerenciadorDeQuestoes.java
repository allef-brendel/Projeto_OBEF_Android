package com.example.obef.Gerenciamento;



import com.example.obef.Gerenciamento.BancoDeQuestoes;
import com.example.obef.Modelos.Questao;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorDeQuestoes {
    List<Questao> questoes;
    Questao atual;
    BancoDeQuestoes banco;

    int indexAtual;
    public GerenciadorDeQuestoes() {
        this.questoes=new ArrayList<Questao>();
        indexAtual=0;
    }
    public Questao getQuestaoBanco() {
        return null;
    }
    public List<Questao> getQuestoesBanco(){
        return null;
    }
    public List<Questao> getDesafio() {
        // TODO Auto-generated method stub
        return null;
    }
}
