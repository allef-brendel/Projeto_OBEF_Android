package com.example.obef.Activity.DesafioDoDia;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.obef.AcertouErrou.Acertou;
import com.example.obef.AcertouErrou.Errou;
import com.example.obef.Activity.MenuQuestoes;
import com.example.obef.Configuracao.ConfiguracaoFirebase;
import com.example.obef.Gerenciamento.Gravador;
import com.example.obef.Helper.Base64Custom;
import com.example.obef.Modelos.Questao;
import com.example.obef.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public abstract class Desafio extends AppCompatActivity {

    private TextView testviwe;
    private TextView justificativa;
    private TextView quantpontos;
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
    private Desafio desafio;
    private final int id= gerarID();
    boolean usouDica;
    Gravador gravador;
    int pontosGanhos;
    Questao questao;
    private FirebaseAuth auth;
    @Override
    public void onBackPressed(){
        startActivity(new Intent(desafio, MenuQuestoes.class));
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pontosGanhos=100;
        usouDica=false;
        gravador=new Gravador();
        gravador.recuperarMoedas();
        questao=gravador.lerQuestao("Questao"+id+".txt");
        Gravador.listaMoedas.set(gerarID()-1,"2");
        gravador.salvarMoedas();
        gravador.salvarQuantQuestoesAcertadas();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        /*if(width==1080 && height==1920){
            setContentView(R.layout.layout_1920_desafiododia);
        }else{
            setContentView(R.layout.activity_tela_desafiododia);
        }*/
        setContentView(R.layout.activity_tela_desafiododia);

        radioButton1 = findViewById(R.id.rd1Id);
        radioButton2 = findViewById(R.id.rd2Id);
        radioButton3 = findViewById(R.id.rd3Id);
        radioButton4 = findViewById(R.id.rd4Id);
        radioButton5 = findViewById(R.id.rd5Id);

        radioButtonEscolhido = findViewById(R.id.rd1Id);
        radioGroup = findViewById(R.id.radioGroup);
        buttonEscolher = findViewById(R.id.escolherId);
        buttonDica = findViewById(R.id.dicaId);
        testviwe =  findViewById(R.id.textoID);

        desafio=this;
        auth= ConfiguracaoFirebase.getFireBaseAutenticacao();
        firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("Acertos").child(Base64Custom.codificarBase64(auth.getCurrentUser().getEmail()));


        buttonDica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        dicaUsada();
                        String string2 = questao.getDica();
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(desafio);
                        alertDialogBuilder.setMessage(string2);
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();

            }
        });

        buttonEscolher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        int idRadioButtonEscolhido = radioGroup.getCheckedRadioButtonId() - radioButton1.getId()+1;
                        Integer i = new Integer(idRadioButtonEscolhido);
                        Boolean escolhida = questao.obterStatus(i);
                        System.out.println(">>>"+radioGroup.getCheckedRadioButtonId()+">>>"+escolhida);
                if(escolhida == null  || radioGroup.getCheckedRadioButtonId()==-1){
                    Toast.makeText(Desafio.this,"Escolha uma alternativa", Toast.LENGTH_LONG).show();
                }
                else if (escolhida == true) {
                            statusRespostaCerta(pontosGanhos);
                            respostaCerta();

                        } else {
                            statusGameOver(i);
                            gameOver();
                        }

            }
        });

                String string = questao.getEnunciado();
                testviwe.setText(string);
                String alternativa1 = questao.getAlternativa1().getTexto();
                radioButton1.setText(alternativa1);
                String alternativa2 = questao.getAlternativa2().getTexto();
                radioButton2.setText(alternativa2);
                String alternativa3 = questao.getAlternativa3().getTexto();
                radioButton3.setText(alternativa3);
                String alternativa4 = questao.getAlternativa4().getTexto();
                radioButton4.setText(alternativa4);
                String alternativa5 = questao.getAlternativa5().getTexto();
                radioButton5.setText(alternativa5);



    }

    private void statusGameOver(int alternativa){
        String justificativa="";
        if(alternativa==1){
            justificativa+=questao.getAlternativa1().getJustificativa();
        }else if(alternativa==2){
            justificativa+=questao.getAlternativa2().getJustificativa();
        }else if(alternativa==3){
            justificativa+=questao.getAlternativa3().getJustificativa();
        }else if(alternativa==4){
            justificativa+=questao.getAlternativa4().getJustificativa();
        }else if(alternativa==5){
            justificativa+=questao.getAlternativa5().getJustificativa();
        }
        gravador.gravarJustificativa(justificativa);

    }

    private void statusRespostaCerta(int pontosGanhos){
        gravador.gravarPontuacaoGanha(pontosGanhos);
    }

    private void gameOver() {
       /* LayoutInflater layoutInflater =  LayoutInflater.from(desafio);
        final View cachorro = layoutInflater.inflate(R.layout.activity_porcotriste,null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(desafio);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Menu Questões",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(desafio, MenuQuestoes.class));
                        finish();
                    }
                });
        alertDialogBuilder.setNegativeButton("Justificativa",
                new DialogInterface.OnClickListener() {
            @Override
                public void onClick(DialogInterface dialog, int which) {
                     startActivity(new Intent(desafio,Errou.class));
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setView(cachorro);
        alertDialog.show();*/
       startActivity(new Intent(desafio,Errou.class));
       finish();
    }

    private void respostaCerta() {
        gravador.salvarPontosGanhos(pontosGanhos);
        gravador.salvarPontosGanhosTotal(pontosGanhos);

               /* LayoutInflater layoutInflater =  LayoutInflater.from(desafio);
                final View cachorro = layoutInflater.inflate(R.layout.activity_porcofeliz,null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(desafio);
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("Menu Questões",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(desafio, MenuQuestoes.class));
                                finish();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.setView(cachorro);
                alertDialog.show();*/
        startActivity(new Intent(desafio,Acertou.class));
        finish();


    }
    public void dicaUsada(){
        this.usouDica=true;
        pontosGanhos=80;
    }
    public abstract int gerarID();






}