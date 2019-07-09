package com.example.obef.Activity;

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
import android.view.animation.AlphaAnimation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.example.obef.Activity.DesafioDoDia.Desafio1;
import com.example.obef.Activity.DesafioDoDia.Desafio10;
import com.example.obef.Activity.DesafioDoDia.Desafio2;
import com.example.obef.Activity.DesafioDoDia.Desafio3;
import com.example.obef.Activity.DesafioDoDia.Desafio4;
import com.example.obef.Activity.DesafioDoDia.Desafio5;
import com.example.obef.Activity.DesafioDoDia.Desafio6;
import com.example.obef.Activity.DesafioDoDia.Desafio7;
import com.example.obef.Activity.DesafioDoDia.Desafio8;
import com.example.obef.Activity.DesafioDoDia.Desafio9;
import com.example.obef.Configuracao.ConfiguracaoFirebase;
import com.example.obef.Gerenciamento.Gravador;
import com.example.obef.Helper.Base64Custom;
import com.example.obef.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Menu extends AppCompatActivity {

    private Button botaoJogar;
    private Button botaoDesafio;
    private Button botaoPontos;
    private Button botaoInformacao;
    private Button botaoDeslogar;
    private Gravador gravador;
    private AlphaAnimation animation;
    private FirebaseAuth autenticacao;

    @Override
    public void onBackPressed(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        gravador=new Gravador();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_menu);

        autenticacao = ConfiguracaoFirebase.getFireBaseAutenticacao();

        botaoDeslogar = findViewById(R.id.botaoDeslogar);
        botaoJogar = findViewById(R.id.botaoJogar);
        botaoDesafio = findViewById(R.id.botaoDesafio);
        botaoPontos = findViewById(R.id.botaoPontos);
        botaoInformacao = findViewById(R.id.botaoInformacao);

        botaoJogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animacaoBotao(botaoJogar);
                startActivity(new Intent(Menu.this, MainActivity.class));
                overridePendingTransition(R.anim.goup, R.anim.godown);
                finish();
            }
        });

        botaoPontos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animacaoBotao(botaoPontos);
                startActivity(new Intent(Menu.this, MenuPontos.class));
                overridePendingTransition(R.anim.goup, R.anim.godown);
                finish();
            }
        });

        botaoDesafio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animacaoBotao(botaoDesafio);
                startActivity(new Intent(Menu.this, MenuQuestoes.class));
                overridePendingTransition(R.anim.goup, R.anim.godown);
                finish();
            }
        });

        botaoInformacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animacaoBotao(botaoInformacao);
                startActivity(new Intent(Menu.this, MenuInformacao.class));
                overridePendingTransition(R.anim.goup, R.anim.godown);
                finish();
            }
        });

        botaoDeslogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Menu.this);
                alertDialogBuilder.setMessage("Deslogar Usuário?");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("Sim",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //gravador.resetarOffline();
                                deslogarUsuario();
                            }
                        });
                alertDialogBuilder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    public void deslogarUsuario(){
        autenticacao.signOut();
        gravador.gravarArquivo("LastUser.txt","1");
        Intent intent = new Intent(Menu.this,ActivityLogin.class);
        startActivity(intent);
        overridePendingTransition(R.anim.goup, R.anim.godown);
        finish();
    }

    public void animacaoBotao(Button button){
        animation = new AlphaAnimation(1, 0); // Altera alpha de visível a invisível
        animation.setDuration(1);
        animation.setInterpolator(new LinearInterpolator());
        button.startAnimation(animation);
    }

}
