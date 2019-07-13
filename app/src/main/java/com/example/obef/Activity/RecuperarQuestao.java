package com.example.obef.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.obef.Cadastro.CadastroActivity;
import com.example.obef.Gerenciamento.Gravador;
import com.example.obef.Helper.Base64Custom;
import com.example.obef.Modelos.Questao;
import com.example.obef.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class RecuperarQuestao extends AppCompatActivity {

    private ProgressDialog pdia;

    Gravador gravador;
    @Override
    public void onBackPressed(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        pdia = new ProgressDialog(RecuperarQuestao.this);
        pdia.setMessage("Carregando...");
        pdia.show();

        gravador=new Gravador();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_questao);
        if(gravador.lerData().equals("0")){
            final DatabaseReference firebase = FirebaseDatabase.getInstance().getReference();
            firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                   if (dataSnapshot.child("Alunos").child(Base64Custom.codificarBase64(gravador.lerUser())).getValue() != null) {
                        gravador.salvarPontosGanhosTotal(dataSnapshot.child("Acertos").child(Base64Custom.codificarBase64(gravador.lerUser())).child("pontos").getValue(Integer.class));
                        String diaLogado=dataSnapshot.child("Acertos").child(Base64Custom.codificarBase64(gravador.lerUser())).child("ultimoDiaLogado").getValue(String.class);
                        System.out.println(diaLogado);
                        if(diaLogado.equals("0")){
                            firebase.child("Acertos").child(Base64Custom.codificarBase64(gravador.lerUser())).child("ultimoDiaLogado").setValue(gravador.lerDataAtual());
                            gravador.salvarData(gravador.lerDataAtual());
                            resetarQuestoes(firebase,dataSnapshot);
                            gravador.gravarOffline();

                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            System.out.println("Rntrou 4");
        }
        else if(gravador.isDataAtual()){
            Boolean boll;
            try{
                String teste =gravador.lerOffline();
                if(teste.equals("1")){
                    boll= true;
                }else{
                    boll=false;
                }
            }catch (Exception e){
                boll=false;
                System.out.println("erro ao ler arquivo offline.txt");

            }

            if(boll){
                abriTelaPrincipal();
            }else {
                gravador.gravarOffline();
                final DatabaseReference firebase = FirebaseDatabase.getInstance().getReference();
                firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        resetarQuestoes(firebase, dataSnapshot);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }
        else {
            System.out.println("Rntrou 6");
            final DatabaseReference firebase = FirebaseDatabase.getInstance().getReference();
            firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.child("Acertos").child(Base64Custom.codificarBase64(gravador.lerUser())).getValue() != null) {
                        //gravador.salvarPontosGanhosTotal(dataSnapshot.child("Acertos").child(Base64Custom.codificarBase64(gravador.lerUser())).child("pontos").getValue(Integer.class));
                        String diaLogado=dataSnapshot.child("Acertos").child(Base64Custom.codificarBase64(gravador.lerUser())).child("ultimoDiaLogado").getValue(String.class);
                        if(diaLogado.equals("0")){
                            System.out.println("Rntrou 1");
                            firebase.child("Acertos").child(Base64Custom.codificarBase64(gravador.lerUser())).child("ultimoDiaLogado").setValue(gravador.lerDataAtual());
                            resetarQuestoes(firebase,dataSnapshot);
                        }
                        else if(!gravador.compararData(diaLogado)){
                            System.out.println("Rntrou 2");
                            firebase.child("Acertos").child(Base64Custom.codificarBase64(gravador.lerUser())).child("ultimoDiaLogado").setValue(gravador.lerDataAtual());
                            resetarQuestoes(firebase,dataSnapshot);

                        }
                        else{
                            System.out.println("Rntrou 3");
                            bloquearDesafio();

                        }

                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void abriTelaPrincipal() {

        Intent intent = new Intent(RecuperarQuestao.this, Menu.class);
        overridePendingTransition(R.anim.goup, R.anim.godown);
        startActivity(intent);
        finish();
    }
    public void resetarQuestoes(DatabaseReference firebase,DataSnapshot dataSnapshot){
        int pontosBD =dataSnapshot.child("Acertos").child(Base64Custom.codificarBase64(gravador.lerUser())).child("pontos").getValue(Integer.class);
        int pontosApp=gravador.lerAtualizacaoPontos();
        int resultado=pontosApp+pontosBD;
        String nivel= dataSnapshot.child("Alunos").child(Base64Custom.codificarBase64(gravador.lerUser())).child("serie").getValue(String.class);
        gravador.resetarAtualizacaoPontos();
        firebase.child("Acertos").child(Base64Custom.codificarBase64(gravador.lerUser())).child("pontos").setValue(resultado);
        //gravador.salvarPontosGanhosTotal(resultado);
        int quant=dataSnapshot.child("contQuestoesN"+nivel).getValue(Integer.class);
        Random random = new Random();
        int[] idQuestoes = new int[quant];
        int[] indiceRandom = new int[10];
        int x = 0;
        while (x < indiceRandom.length) {
            int y = 0;
            int numRand = random.nextInt(quant);
            while (y < x) {
                if (indiceRandom[y] == numRand) {
                    numRand = random.nextInt(quant);
                    y = 0;
                } else {
                    y++;
                }
            }
            indiceRandom[x] = numRand;
            x++;
        }

        int cont = 0;
        for (DataSnapshot d : dataSnapshot.child("idquestaoN"+nivel).getChildren()) {

            idQuestoes[cont] = d.getValue(Integer.class);
            System.out.println(d.getValue(Integer.class).toString());
            cont++;
        }

        Questao q1 = getQuestaoBD(firebase, dataSnapshot, idQuestoes[indiceRandom[0]], nivel);
        Questao q2 = getQuestaoBD(firebase, dataSnapshot, idQuestoes[indiceRandom[1]], nivel);
        Questao q3 = getQuestaoBD(firebase, dataSnapshot, idQuestoes[indiceRandom[2]], nivel);
        Questao q4 = getQuestaoBD(firebase, dataSnapshot, idQuestoes[indiceRandom[3]], nivel);
        Questao q5 = getQuestaoBD(firebase, dataSnapshot, idQuestoes[indiceRandom[4]], nivel);
        Questao q6 = getQuestaoBD(firebase, dataSnapshot, idQuestoes[indiceRandom[5]], nivel);
        Questao q7 = getQuestaoBD(firebase, dataSnapshot, idQuestoes[indiceRandom[6]], nivel);
        Questao q8 = getQuestaoBD(firebase, dataSnapshot, idQuestoes[indiceRandom[7]], nivel);
        Questao q9 = getQuestaoBD(firebase, dataSnapshot, idQuestoes[indiceRandom[8]], nivel);
        Questao q10 = getQuestaoBD(firebase, dataSnapshot, idQuestoes[indiceRandom[9]], nivel);
        gravador.salvarQuestao("Questao1.txt", q1);
        gravador.salvarQuestao("Questao2.txt", q2);
        gravador.salvarQuestao("Questao3.txt", q3);
        gravador.salvarQuestao("Questao4.txt", q4);
        gravador.salvarQuestao("Questao5.txt", q5);
        gravador.salvarQuestao("Questao6.txt", q6);
        gravador.salvarQuestao("Questao7.txt", q7);
        gravador.salvarQuestao("Questao8.txt", q8);
        gravador.salvarQuestao("Questao9.txt", q9);
        gravador.salvarQuestao("Questao10.txt", q10);
        gravador.salvarData(gravador.lerData());

        pdia.cancel();
        abriTelaPrincipal();
    }
    public void bloquearDesafio(){
        gravador.bloquearDesafio();
        abriTelaPrincipal();
    }
    private Questao getQuestaoBD(DatabaseReference firebase,DataSnapshot dataSnapshot,int idRandom,String nivel){
        Questao q=dataSnapshot.child("questoes").child(nivel).child("" + idRandom).getValue(Questao.class);
        System.out.println(q.getDica());
        return q;
    }

}
