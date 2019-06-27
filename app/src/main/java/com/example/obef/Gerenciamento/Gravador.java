package com.example.obef.Gerenciamento;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.example.obef.Activity.MenuQuestoes;
import com.example.obef.Modelos.Alternativa;
import com.example.obef.Modelos.Aluno;
import com.example.obef.Modelos.Questao;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Gravador {
    public static List<String> listaMoedas;
    public void gravarArquivo(String nomeArquivo, String texto){
        try {
            OutputStreamWriter buff=new OutputStreamWriter(new FileOutputStream("/data/data/com.example.obef/files/"+nomeArquivo),"UTF-8");
            buff.write(texto);
            buff.close();
        } catch (FileNotFoundException e) {
            System.out.println("1");
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            System.out.println("2");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("3");
            e.printStackTrace();
        }
    }

    public String lerArquivo(String nomeArquivo){
        String s="";
        BufferedReader buff;
        try {
            buff= new BufferedReader(new InputStreamReader(new FileInputStream("/data/data/com.example.obef/files/"+nomeArquivo),"UTF-8"));
            String linha=buff.readLine();
            while(linha!=null){
                s+=linha+"\n";
                linha=buff.readLine();
            }
            buff.close();
        } catch (UnsupportedEncodingException e) {
            System.out.println("4");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.out.println("5");
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            System.out.println("6");
            e.printStackTrace();
        }
        return s;
    }

    /*public void salvarAluno(Aluno a){
        String s="";
        s+=a.getNome()+"|";
        s+=a.getCidade()+"|";
        s+=a.getPontuacao()+"|";
        gravarArquivo("Aluno.txt",s);
    }
    public Aluno lerAluno(Context ctx){
        Aluno a=new Aluno();
        String arquivo=lerArquivo("Aluno.txt");
        Scanner scan=new Scanner(arquivo).useDelimiter("\\|");
        System.out.println(arquivo);
        a.setNome(scan.next());
        a.setCidade(scan.next());
        a.setPontuacao(Integer.parseInt(scan.next()));
        scan.close();


        return a;
    }*/
    public void salvarQuestao(String nomeArquivo, Questao questao){
        gravarArquivo(nomeArquivo,questao.toStringSave());
    }
    public Questao lerQuestao(String nomeArquivo){

        String arquivo = lerArquivo(nomeArquivo);
        Scanner sc=new Scanner(arquivo).useDelimiter("\\|");
        Alternativa a1=new Alternativa(Boolean.parseBoolean(sc.next()),sc.next(),sc.next());
        Alternativa a2=new Alternativa(Boolean.parseBoolean(sc.next()),sc.next(),sc.next());
        Alternativa a3=new Alternativa(Boolean.parseBoolean(sc.next()),sc.next(),sc.next());
        Alternativa a4=new Alternativa(Boolean.parseBoolean(sc.next()),sc.next(),sc.next());
        Alternativa a5=new Alternativa(Boolean.parseBoolean(sc.next()),sc.next(),sc.next());
        String dica=sc.next();
        String dificuldade=sc.next();
        String enunciado=sc.next();
        int nivel=Integer.parseInt(sc.next());
        int valor=Integer.parseInt(sc.next());
        Questao questao=new Questao(a1,a2,a3,a4,a5,dica,dificuldade,enunciado,nivel,valor);
        sc.close();
        return  questao;
    }
    public boolean isDataAtual(){

        String dataLida=lerData();
        String dataAtual=lerDataAtual();
        if(dataLida.length()<1){
            salvarData(dataAtual);
            salvarAtualizacaoPontos();
            resetarPontos();
            resetarMoedas();
            resetarPontosTotais();
            resetarQuantAcertos();
            System.out.println("Rodou aqui kkkkkk");
            return false;
        }
        /*if(lerArquivo("Moeda.txt").length()<1){
            resetarMoedas();
            resetarPontos();
        }*/
        int diaAtual, diaArquivo,mesAtual,mesArquivo, anoAtual, anoArquivo;
        diaArquivo=Integer.parseInt(dataLida.substring(0,2));
        mesArquivo=Integer.parseInt(dataLida.substring(2,4));
        anoArquivo=Integer.parseInt(dataLida.substring(4,6));
        diaAtual=Integer.parseInt(dataAtual.substring(0,2));
        mesAtual=Integer.parseInt(dataAtual.substring(2,4));
        anoAtual=Integer.parseInt(dataAtual.substring(4,6));
        System.out.println(diaArquivo+"   "+diaAtual);
        boolean flag=true;
        if(anoAtual>anoArquivo){
            System.out.println("Erro data 1");
            flag= false;
        }else if(mesAtual>mesArquivo){
            System.out.println("Erro data 2");
            flag= false;
        }else if(diaAtual>diaArquivo){
            System.out.println("Erro data 3");
            flag= false;
        }
        System.out.println("Agora rodou esse kkkkkkkk");
        if(!flag){
            gravarArquivo("Data.txt",dataAtual);
            resetarMoedas();
            salvarAtualizacaoPontos();
            //salvarPontosGanhosTotal(lerPontos());
            resetarPontos();
            resetarQuantAcertos();


        }
        System.out.println("Flag = "+flag);
        return flag;

    }

    public void salvarPontosGanhosTotal(int pontos) {
        System.out.println("aloua");
        String s="";
        int pontosArquivo= lerPontosTotais();
        int resultado =pontos+pontosArquivo;
        s+=resultado+"|";
        gravarArquivo("PontosTotais.txt",s);
    }

    public int lerPontosTotais() {
        Scanner scanner=new Scanner(lerArquivo("PontosTotais.txt")).useDelimiter("\\|");
        int pontos=Integer.parseInt(scanner.next());
        scanner.close();
        return pontos;
    }

    public void resetarMoedas(){
        String a="1|1|1|1|1|1|1|1|1|1|";
        gravarArquivo("Moeda.txt",a);
    }
    public void recuperarMoedas(){
        List<String> list=new ArrayList<String>();
        String arquivo=lerArquivo("Moeda.txt");
        Scanner sc=new Scanner(arquivo).useDelimiter("\\|");
        while(sc.hasNext()) list.add(sc.next());
        Gravador.listaMoedas=list;
        sc.close();


    }
    public void salvarMoedas(){
        String s="";
        for(String st: Gravador.listaMoedas){
            s+=st+"|";
        }
        gravarArquivo("Moeda.txt",s);
    }
    public int lerPontos(){
        Scanner scanner=new Scanner(lerArquivo("Pontos.txt")).useDelimiter("\\|");
        int pontos=Integer.parseInt(scanner.next());
        scanner.close();
        return pontos;
    }
    public void salvarPontosGanhos(int pontos){
        String s="";
        int pontosArquivo= lerPontos();
        int resultado =pontos+pontosArquivo;
        s+=resultado+"|";
        gravarArquivo("Pontos.txt",s);
    }
    public void resetarPontos(){
        String a="0|";
        gravarArquivo("Pontos.txt",a);
    }
    public void resetarPontosTotais(){
        String a="0|";
        gravarArquivo("PontosTotais.txt",a);
    }
    public void salvarQuantQuestoesAcertadas(){
        int quant=lerQuantQuestoesAcertadas();
        quant++;
        String s="";
        s+=quant+"|";
        gravarArquivo("QuantAcertos.txt",s);

    }
    public int lerQuantQuestoesAcertadas(){
        Scanner scanner=new Scanner(lerArquivo("QuantAcertos.txt")).useDelimiter("\\|");
        int pontos=Integer.parseInt(scanner.next());
        scanner.close();
        return pontos;
    }
    public void resetarQuantAcertos(){
        String a="0|";
        gravarArquivo("QuantAcertos.txt",a);
    }
    public void salvarAtualizacaoPontos(){
        int quant=lerPontos();
        String s="";
        s+=quant+"|";
        gravarArquivo("AtualizacaoPontos.txt",s);

    }
    public int lerAtualizacaoPontos(){
        Scanner scanner=new Scanner(lerArquivo("AtualizacaoPontos.txt")).useDelimiter("\\|");
        int pontos=Integer.parseInt(scanner.next());
        scanner.close();
        return pontos;
    }
    public void resetarAtualizacaoPontos(){
        String a="0|";
        gravarArquivo("AtualizacaoPontos.txt",a);
    }
    public void salvarUser(String email){
        String s=email+"|";
        gravarArquivo("User.txt",s);

    }
    public String lerUser(){
        Scanner scanner=new Scanner(lerArquivo("User.txt")).useDelimiter("\\|");
        String s="";
        if (scanner.hasNext()){
            s+=scanner.next();
        }
        scanner.close();
        return s;
    }
    public void salvarData(String ddMMyyyy){
        String s="";
        s+=ddMMyyyy+"|";
        gravarArquivo("Data.txt",s);
    }
    public String lerData(){
        Scanner scanner=new Scanner(lerArquivo("Data.txt")).useDelimiter("\\|");
        String s=scanner.next();
        scanner.close();
        return s;
    }
    public void resetarData(){
        String s="|";
        gravarArquivo("Data.txt",s);
    }
    public String lerDataAtual(){
        Date data=new Date();
        SimpleDateFormat dateFormat;
        dateFormat=new SimpleDateFormat("ddMMyyyy");
        String dataAtual=dateFormat.format(data).toString();
        return dataAtual;
    }
    public boolean compararData(String dataRecebida){
        String dataLida=dataRecebida;
        String dataAtual=lerDataAtual();
        int diaAtual, diaArquivo,mesAtual,mesArquivo, anoAtual, anoArquivo;
        diaArquivo=Integer.parseInt(dataLida.substring(0,2));
        mesArquivo=Integer.parseInt(dataLida.substring(2,4));
        anoArquivo=Integer.parseInt(dataLida.substring(4,6));
        diaAtual=Integer.parseInt(dataAtual.substring(0,2));
        mesAtual=Integer.parseInt(dataAtual.substring(2,4));
        anoAtual=Integer.parseInt(dataAtual.substring(4,6));
        boolean flag=true;
        if(anoAtual>anoArquivo){
            flag= false;
        }else if(mesAtual>mesArquivo){
            flag= false;
        }else if(diaAtual>diaArquivo){
            flag= false;
        }
        return flag;
    }


    public void bloquearDesafio() {
        String a="2|2|2|2|2|2|2|2|2|2|";
        gravarArquivo("Moeda.txt",a);
    }

    public String lerOffline() {
        Scanner scanner=new Scanner(lerArquivo("Offline.txt")).useDelimiter("\\|");
        String s=scanner.next();
        scanner.close();
        return s;
    }
    public void gravarOffline(){
        String a="1|";
        gravarArquivo("Offline.txt",a);
    }
    public void resetarOffline(){
        String a="0|";
        gravarArquivo("Offline.txt",a);
    }

    public void gravarJustificativa(String justificativa) {
            gravarArquivo("Errou.txt",justificativa);

    }

    public void gravarPontuacaoGanha(int pontosGanhos) {
        gravarArquivo("Acertou.txt",""+pontosGanhos);
    }
    public String recuperaStatusErrou(){
        return lerArquivo("Errou.txt");
    }
    public String recuperaStatusAcertou(){
        return lerArquivo("Acertou.txt");
    }
}
