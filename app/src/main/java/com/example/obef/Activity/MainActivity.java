package com.example.obef.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.obef.Gerenciamento.BancoDeQuestoes;
import com.example.obef.Modelos.Escola;
import com.example.obef.Modelos.Questao;
import com.example.obef.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private TextView testviwe;
    private Button buttonEscolher;
    private Button buttonDica;
    private DatabaseReference firebaseDatabase;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioButton radioButton4;
    private RadioButton radioButton5;
    private RadioButton radioButtonEscolhido;
    private RadioGroup radioGroup;
    private List<String> idQuestoes;
    private List<String> questoesBD;
    private String questao = "-LbHIqN9xlO1IauQJ3hn";
    private Random rd;
    private int pontos;
    private boolean iniciado;
    int numQuestao;
    private Escola escola;
    private int id;
    @Override
    public void onBackPressed(){
        startActivity(new Intent(MainActivity.this,Menu.class));
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        if(width==1080 && height==1920){
            setContentView(R.layout.layout_1920_jogar);
        }else{
            setContentView(R.layout.activity_tela_jogar);
        }


        rd = new Random();
        idQuestoes = new ArrayList<String>();
        questoesBD = new ArrayList<String>();
        //id=rd.nextInt(59)+1;

        radioButton1 = findViewById(R.id.rd1Id);
        radioButton2 = findViewById(R.id.rd2Id);
        radioButton3 = findViewById(R.id.rd3Id);
        radioButton4 = findViewById(R.id.rd4Id);
        radioButton5 = findViewById(R.id.rd5Id);


        radioButtonEscolhido = findViewById(R.id.rd1Id);

        radioGroup = findViewById(R.id.radioGroup);

        buttonEscolher = findViewById(R.id.escolherId);
        buttonDica = findViewById(R.id.dicaId);

        testviwe = findViewById(R.id.textoID);

        firebaseDatabase = FirebaseDatabase.getInstance().getReference();


        buttonDica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String string2 = (String) dataSnapshot.child("Questoes").child(""+id).child("dica").getValue();
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                        alertDialogBuilder.setMessage(string2);
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        buttonEscolher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int idRadioButtonEscolhido = radioGroup.getCheckedRadioButtonId() - radioButton1.getId()+1;
                        Integer i = new Integer(idRadioButtonEscolhido);
                        //testviwe.setText(i.toString());
                        Boolean escolhida = (Boolean) dataSnapshot.child("Questoes").child(""+id).child("alternativa" + i.toString()).child("status").getValue();
                        if(escolhida == null){
                            Toast.makeText(MainActivity.this,"Escolha uma alternativa", Toast.LENGTH_LONG).show();
                        }
                        else if (escolhida == true) {
                            radioButtonEscolhido = findViewById(idRadioButtonEscolhido);
                           respostaCerta();
                        } else {
                            gameOver();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });


        firebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                id=rd.nextInt(dataSnapshot.child("quantQuestoes").getValue(Integer.class))+1;
                String string = (String) dataSnapshot.child("Questoes").child(""+id).child("enunciado").getValue();
                testviwe.setText(string);
                String alternativa1 = (String) dataSnapshot.child("Questoes").child(""+id).child("alternativa1").child("texto").getValue();
                radioButton1.setText(alternativa1);
                String alternativa2 = (String) dataSnapshot.child("Questoes").child(""+id).child("alternativa2").child("texto").getValue();
                radioButton2.setText(alternativa2);
                String alternativa3 = (String) dataSnapshot.child("Questoes").child(""+id).child("alternativa3").child("texto").getValue();
                radioButton3.setText(alternativa3);
                String alternativa4 = (String) dataSnapshot.child("Questoes").child(""+id).child("alternativa4").child("texto").getValue();
                radioButton4.setText(alternativa4);
                String alternativa5 = (String) dataSnapshot.child("Questoes").child(""+id).child("alternativa5").child("texto").getValue();
                radioButton5.setText(alternativa5);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        }
    private void gameOver() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setMessage("Resposta Incorreta!");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Nova Questão",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                });
        alertDialogBuilder.setNegativeButton("Voltar ao Menu",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(MainActivity.this,Menu.class));
                        finish();
                    }

                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void respostaCerta() {
        firebaseDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setMessage("Resposta Correta!" );
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("Nova Questão",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                proximaQuestao();
                            }
                        });
                alertDialogBuilder.setNegativeButton("Voltar ao Menu",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(MainActivity.this,Menu.class));
                                finish();
                            }

                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    private void proximaQuestao(){
        startActivity(new Intent(MainActivity.this,MainActivity.class));
        finish();
    }
    private void getPontuacao(){
        if(iniciado) {
            System.out.println("Pontuacao iniciado");
            firebaseDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String asd = dataSnapshot.child("Respostas").child(""+id).child("pontosTotal").getValue().toString();
                    pontos = Integer.parseInt(asd);
                    System.out.print("O jogador possui " + pontos);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("Cancelado");
                }
            });
        }
    }
    public void gravarArquivo(String nomeArquivo, String texto){
        try {
            OutputStreamWriter buff=new OutputStreamWriter(new FileOutputStream("/data/data/com.example.obef/files/Questao1.txt"),"UTF-8");
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
            buff= new BufferedReader(new InputStreamReader(new FileInputStream("/data/data/com.example.obef/files/Questao1.txt"),"UTF-8"));
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
        } catch (IOException e) {
            System.out.println("6");
            e.printStackTrace();
        }
        return s;
    }




}