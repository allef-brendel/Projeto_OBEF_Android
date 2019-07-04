package com.example.obef.Splash;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.util.DisplayMetrics;

import com.example.obef.Configuracao.ConfiguracaoFirebase;
import com.example.obef.Gerenciamento.Gravador;
import com.example.obef.Helper.Base64Custom;
import com.example.obef.Modelos.Questao;
import com.example.obef.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.Random;

public class Splash1 extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    public static int inteiro=0;
    Gravador gravador;
    FirebaseAuth auth;
    @Override
    public void onBackPressed(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        auth = ConfiguracaoFirebase.getFireBaseAutenticacao();
        gravador=new Gravador();
        super.onCreate(savedInstanceState);
        criarPastaFile();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        System.out.println("h>"+height+" w>"+width);

        setContentView(R.layout.activity_splash1);
        iniciarActivity();



    }
    private void iniciarActivity(){
        new Handler().postDelayed(new Runnable() {
            /*
             * Exibindo splash com um timer.
             */
            @Override
            public void run() {
                // Esse método será executado sempre que o timer acabar
                // E inicia a activity principal
                Intent i = new Intent(Splash1.this, Splash2.class);
                startActivity(i);
                overridePendingTransition(R.anim.goup, R.anim.goup);

                // Fecha esta activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
    public void criarPastaFile(){
        try {
            FileOutputStream file=openFileOutput("teste.txt",MODE_PRIVATE);
            String s="asd";
            file.write(s.getBytes());
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
