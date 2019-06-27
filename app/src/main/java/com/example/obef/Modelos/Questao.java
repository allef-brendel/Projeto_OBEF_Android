package com.example.obef.Modelos;

public class Questao {
    Alternativa alternativa1;
    Alternativa alternativa2;
    Alternativa alternativa3;
    Alternativa alternativa4;
    Alternativa alternativa5;
    String dica;
    String dificuldade;
    String enunciado;
    int nivel;
    int valor;

    public Questao() {
    }

    public Questao(Alternativa alternativa1, Alternativa alternativa2, Alternativa alternativa3, Alternativa alternativa4, Alternativa alternativa5, String dica, String dificuldade, String enunciado, int nivel, int valor) {
        this.alternativa1 = alternativa1;
        this.alternativa2 = alternativa2;
        this.alternativa3 = alternativa3;
        this.alternativa4 = alternativa4;
        this.alternativa5 = alternativa5;
        this.dica = dica;
        this.dificuldade = dificuldade;
        this.enunciado = enunciado;
        this.nivel = nivel;
        this.valor = valor;
    }

    public Alternativa getAlternativa1() {
        return alternativa1;
    }

    public void setAlternativa1(Alternativa alternativa1) {
        this.alternativa1 = alternativa1;
    }

    public Alternativa getAlternativa2() {
        return alternativa2;
    }

    public void setAlternativa2(Alternativa alternativa2) {
        this.alternativa2 = alternativa2;
    }

    public Alternativa getAlternativa3() {
        return alternativa3;
    }

    public void setAlternativa3(Alternativa alternativa3) {
        this.alternativa3 = alternativa3;
    }

    public Alternativa getAlternativa4() {
        return alternativa4;
    }

    public void setAlternativa4(Alternativa alternativa4) {
        this.alternativa4 = alternativa4;
    }

    public Alternativa getAlternativa5() {
        return alternativa5;
    }

    public void setAlternativa5(Alternativa alternativa5) {
        this.alternativa5 = alternativa5;
    }

    public String getDica() {
        return dica;
    }

    public void setDica(String dica) {
        this.dica = dica;
    }

    public String getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(String dificuldade) {
        this.dificuldade = dificuldade;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public int getNivel() {
        return nivel;
    }
    public void setNivel(int nivel){
        this.nivel=nivel;
    }


    public int getValor() {
        return valor;
    }

    public void setValor(int valor){
        this.valor=valor;
    }
    public String toStringSave(){
        String s="";
        s+=alternativa1.getSaveString()+alternativa2.getSaveString()+alternativa3.getSaveString()
                +alternativa4.getSaveString()+alternativa5.getSaveString();
        s+=dica+"|"+dificuldade+"|"+enunciado+"|"+nivel+"|"+valor+"|";
        return s;
    }
    public boolean obterStatus(int questao){
        if(questao==1){
            return alternativa1.getStatus();
        }else if(questao==2){
            return alternativa2.getStatus();
        }else if(questao==3){
            return alternativa3.getStatus();
        }else if(questao==4){
            return alternativa4.getStatus();
        }else{
            return alternativa5.getStatus();
        }

    }


}