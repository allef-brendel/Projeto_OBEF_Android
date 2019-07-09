package com.example.obef.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.obef.Activity.DesafioDoDia.Desafio;
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
    private ProgressDialog pdia;
    private RadioGroup radioGroup;
    private Random rd;
    private int id;
    private Dialog MyDialog;
    private AlphaAnimation animation;

    @Override
    public void onBackPressed(){
        startActivity(new Intent(MainActivity.this,Menu.class));
        overridePendingTransition(R.anim.goup, R.anim.godown);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pdia = new ProgressDialog(MainActivity.this);
        pdia.setMessage("Carregando...");
        pdia.show();

        setarContent();
        rd = new Random();
        setarIdComponentes();
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();

        setClickButtonDica();
        setClickButtonEscolher();
        setarStringsDaQuestao();
    }

    private void setarIdComponentes() {
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
    }

    private void setarStringsDaQuestao() {
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

                pdia.cancel();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void setClickButtonDica() {
        buttonDica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animacaoBotao(buttonDica);
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
    }

    private void setClickButtonEscolher() {
        buttonEscolher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animacaoBotao(buttonEscolher);
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
    }

    private void setarContent() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

     //   if(width==1080 && height==1920){
      //      setContentView(R.layout.layout_1920_jogar);
      //  }else{
            setContentView(R.layout.activity_tela_jogar);
       // }
    }

    private void gameOver() {

        MyDialog = new Dialog(MainActivity.this);
        MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyDialog.setContentView(R.layout.alert_dialog_modificado);
        MyDialog.setCancelable(false);
        MyDialog.setTitle("Resposta Errada");

        Button hello = MyDialog.findViewById(R.id.hello);
        Button hello2 = MyDialog.findViewById(R.id.hello2);
        ImageView image = MyDialog.findViewById(R.id.imageViewDialog);
        TextView textMeio = MyDialog.findViewById(R.id.textMeio);

        image.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.porcotriste));

        hello.setEnabled(true);
        hello2.setEnabled(true);

        hello.setText("Nova Questão");
        hello2.setText("Voltar ao Menu");
        textMeio.setText("Resposta Errada");

        hello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proximaQuestao();
            }
        });

        hello2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Menu.class));
                overridePendingTransition(R.anim.goup, R.anim.godown);
                finish();
            }
        });

        MyDialog.show();

    }

    private void respostaCerta() {
        firebaseDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                MyDialog = new Dialog(MainActivity.this);
                MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                MyDialog.setContentView(R.layout.alert_dialog_modificado);
                MyDialog.setCancelable(false);
                MyDialog.setTitle("Resposta Certa");

                Button hello = MyDialog.findViewById(R.id.hello);
                Button hello2 = MyDialog.findViewById(R.id.hello2);
                TextView textMeio = MyDialog.findViewById(R.id.textMeio);

                ImageView image = MyDialog.findViewById(R.id.imageViewDialog);

                image.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.porcofeliz));

                hello.setEnabled(true);
                hello2.setEnabled(true);

                hello.setText("Nova Questão");
                hello2.setText("Voltar ao Menu");
                textMeio.setText("Resposta Correta");

                hello.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        proximaQuestao();
                    }
                });

                hello2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this,Menu.class));
                        overridePendingTransition(R.anim.goup, R.anim.godown);
                        finish();
                    }
                });

                MyDialog.show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    private void proximaQuestao(){
        startActivity(new Intent(MainActivity.this,MainActivity.class));
        overridePendingTransition(R.anim.goup, R.anim.godown);
        finish();
    }

    public void animacaoBotao(Button button){
        animation = new AlphaAnimation(1, 0); // Altera alpha de visível a invisível
        animation.setDuration(200);
        animation.setInterpolator(new LinearInterpolator());
        button.startAnimation(animation);
    }

}