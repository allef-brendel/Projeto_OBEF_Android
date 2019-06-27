package com.example.obef.Gerenciamento;

import com.example.obef.Modelos.Questao;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BancoDeQuestoes {
    private DatabaseReference firebaseDatabase;
    private Questao q2;

    public BancoDeQuestoes(){
        firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("questões");

    }
    public void recuperarQuestao(String idQuestao){

        firebaseDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Questao q1=dataSnapshot.child("-LbHL9frariPAQaU0HTh").getValue(Questao.class);
                q2=q1;
                System.out.println(q1.getDificuldade()+" dentro do listener");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }
    public static List<String> recuperaTextoDeArquivo(String nomeArquivo)
            throws IOException {
        BufferedReader leitor = null;
        List<String> textoLido = new ArrayList<String>();
        try {
            leitor = new BufferedReader(new FileReader(nomeArquivo));
            String texto = null;
            do {
                texto = leitor.readLine(); // lê a próxima linha do arquivo
                if (texto != null){
                    textoLido.add(texto);
                }
            } while(texto != null); //vai ser null quando chegar no fim do arquivo
        } finally {
            if (leitor!=null){
                leitor.close(); //fecha o stream(fluxo) de leitura
            }
        }
        return textoLido;
    }
    public static void gravaTextoEmArquivo(List<String> texto, String nomeArquivo)
            throws IOException {
        BufferedWriter gravador = null;
        try {
            gravador = new BufferedWriter(new FileWriter(nomeArquivo));
            for (String s: texto){
                gravador.write(s+"\n");
            }
        } finally {
            if (gravador!=null){
                gravador.close(); //fecha o stream(fluxo) de escrita
            }
        }
    }
    public void gravarQuestoes(List<Questao> lista){
        for (Questao q: lista) {

        }
    }

}