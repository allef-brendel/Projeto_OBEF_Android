package com.example.obef.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.example.obef.Gerenciamento.Gravador;
import com.example.obef.R;

public  class MenuQuestoes extends AppCompatActivity {

    private ImageView botaoMoeda1;
    private ImageView botaoMoeda2;
    private ImageView botaoMoeda3;
    private ImageView botaoMoeda4;
    private ImageView botaoMoeda5;
    private ImageView botaoMoeda6;
    private ImageView botaoMoeda7;
    private ImageView botaoMoeda8;
    private ImageView botaoMoeda9;
    private ImageView botaoMoeda10;

    private ImageView quantQuestoesAcertadas;
    private Gravador  gravador;
    @Override
    public void onBackPressed(){
        startActivity(new Intent(MenuQuestoes.this, Menu.class));
        overridePendingTransition(R.anim.goup, R.anim.godown);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gravador=new Gravador();
        gravador.recuperarMoedas();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        if(width==1080 && height==1920){
            setContentView(R.layout.layout_1920_moedas);
        }else{
            setContentView(R.layout.activity_tela_moedas);
        }


        botaoMoeda1 = findViewById(R.id.botaomoeda1);
        botaoMoeda2 = findViewById(R.id.botaomoeda2);
        botaoMoeda3 = findViewById(R.id.botaomoeda3);
        botaoMoeda4 = findViewById(R.id.botaomoeda4);
        botaoMoeda5 = findViewById(R.id.botaomoeda5);
        botaoMoeda6 = findViewById(R.id.botaomoeda6);
        botaoMoeda7 = findViewById(R.id.botaomoeda7);
        botaoMoeda8 = findViewById(R.id.botaomoeda8);
        botaoMoeda9 = findViewById(R.id.botaomoeda9);
        botaoMoeda10 = findViewById(R.id.botaomoeda10);
        quantQuestoesAcertadas = findViewById(R.id.quantQuestoesAcertadas);

        if (Gravador.listaMoedas.get(0).equals("1")){
            botaoMoeda1.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.moeda1));
        }else{
            botaoMoeda1.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.moedavirada));
        }

        if (Gravador.listaMoedas.get(1).equals("1")){
            botaoMoeda2.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.moeda2));
        }else{
            botaoMoeda2.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.moedavirada));

        }

        if (Gravador.listaMoedas.get(2).equals("1")){
            botaoMoeda3.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.moeda3));
        }else{
            botaoMoeda3.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.moedavirada));

        }
        if (Gravador.listaMoedas.get(3).equals("1")){
            botaoMoeda4.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.moeda4));
        }else{
            botaoMoeda4.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.moedavirada));

        }
        if (Gravador.listaMoedas.get(4).equals("1")){
            botaoMoeda5.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.moeda5));
        }else{
            botaoMoeda5.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.moedavirada));

        }
        if (Gravador.listaMoedas.get(5).equals("1")){
            botaoMoeda6.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.moeda6));
        }else{
            botaoMoeda6.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.moedavirada));

        }
        if (Gravador.listaMoedas.get(6).equals("1")){
            botaoMoeda7.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.moeda7));
        }else{
            botaoMoeda7.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.moedavirada));

        }
        if (Gravador.listaMoedas.get(7).equals("1")){
            botaoMoeda8.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.moeda8));
        }else{
            botaoMoeda8.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.moedavirada));

        }
        if (Gravador.listaMoedas.get(8).equals("1")){
            botaoMoeda9.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.moeda9));
        }else{
            botaoMoeda9.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.moedavirada));

        }
        if (Gravador.listaMoedas.get(9).equals("1")){
            botaoMoeda10.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.moeda10));
        }else{
            botaoMoeda10.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.moedavirada));
        }


        botaoMoeda1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Gravador.listaMoedas.get(0).equals("1")) {
                    startActivity(new Intent(MenuQuestoes.this, Desafio1.class));
                    overridePendingTransition(R.anim.goup, R.anim.godown);
                    finish();
                }else {
                    Toast.makeText(MenuQuestoes.this, "Desafio já concluido", Toast.LENGTH_SHORT).show();
                }
            }
        });

        botaoMoeda2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Gravador.listaMoedas.get(1).equals("1")) {
                    startActivity(new Intent(MenuQuestoes.this, Desafio2.class));
                    overridePendingTransition(R.anim.goup, R.anim.godown);
                    finish();
                }else {
                    Toast.makeText(MenuQuestoes.this, "Desafio já concluido", Toast.LENGTH_SHORT).show();
                }
            }
        });

        botaoMoeda3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Gravador.listaMoedas.get(2).equals("1")) {
                    startActivity(new Intent(MenuQuestoes.this, Desafio3.class));
                    overridePendingTransition(R.anim.goup, R.anim.godown);
                    finish();
                }else {
                    Toast.makeText(MenuQuestoes.this, "Desafio já concluido", Toast.LENGTH_SHORT).show();
                }
            }
        });

        botaoMoeda4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Gravador.listaMoedas.get(3).equals("1")) {
                    startActivity(new Intent(MenuQuestoes.this, Desafio4.class));
                    overridePendingTransition(R.anim.goup, R.anim.godown);
                    finish();
                }else {
                    Toast.makeText(MenuQuestoes.this, "Desafio já concluido", Toast.LENGTH_SHORT).show();
                }
            }
        });

        botaoMoeda5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Gravador.listaMoedas.get(4).equals("1")) {
                    startActivity(new Intent(MenuQuestoes.this, Desafio5.class));
                    overridePendingTransition(R.anim.goup, R.anim.godown);
                    finish();
                }else {
                    Toast.makeText(MenuQuestoes.this, "Desafio já concluido", Toast.LENGTH_SHORT).show();
                }
            }
        });

        botaoMoeda6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Gravador.listaMoedas.get(5).equals("1")) {
                    startActivity(new Intent(MenuQuestoes.this, Desafio6.class));
                    overridePendingTransition(R.anim.goup, R.anim.godown);
                    finish();
                }else {
                    Toast.makeText(MenuQuestoes.this, "Desafio já concluido", Toast.LENGTH_SHORT).show();
                }
            }
        });

        botaoMoeda7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Gravador.listaMoedas.get(6).equals("1")) {
                    startActivity(new Intent(MenuQuestoes.this, Desafio7.class));
                    overridePendingTransition(R.anim.goup, R.anim.godown);
                    finish();
                }else {
                    Toast.makeText(MenuQuestoes.this, "Desafio já concluido", Toast.LENGTH_SHORT).show();
                }
            }
        });

        botaoMoeda8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Gravador.listaMoedas.get(7).equals("1")) {
                    startActivity(new Intent(MenuQuestoes.this, Desafio8.class));
                    overridePendingTransition(R.anim.goup, R.anim.godown);
                    finish();
                }else {
                    Toast.makeText(MenuQuestoes.this, "Desafio já concluido", Toast.LENGTH_SHORT).show();
                }
            }
        });

        botaoMoeda9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Gravador.listaMoedas.get(8).equals("1")) {
                    startActivity(new Intent(MenuQuestoes.this, Desafio9.class));
                    overridePendingTransition(R.anim.goup, R.anim.godown);
                    finish();
                }else {
                    Toast.makeText(MenuQuestoes.this, "Desafio já concluido", Toast.LENGTH_SHORT).show();
                }
            }
        });

        botaoMoeda10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Gravador.listaMoedas.get(9).equals("1")) {
                    startActivity(new Intent(MenuQuestoes.this, Desafio10.class));
                    overridePendingTransition(R.anim.goup, R.anim.godown);
                    finish();
                }else {
                    Toast.makeText(MenuQuestoes.this, "Desafio já concluido", Toast.LENGTH_SHORT).show();
                }
            }
        });


        switch (gravador.lerQuantQuestoesAcertadas()){
            case 0:
                quantQuestoesAcertadas.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.zerodez));
                break;
            case 1:
                quantQuestoesAcertadas.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.zeroum));
                break;
            case 2:
                quantQuestoesAcertadas.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.zerodois));
                break;
            case 3:
                quantQuestoesAcertadas.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.zerotreis));
                break;
            case 4:
                quantQuestoesAcertadas.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.zeroquatro));
                break;
            case 5:
                quantQuestoesAcertadas.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.zerocinco));
                break;
            case 6:
                quantQuestoesAcertadas.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.zeroseis));
                break;
            case 7:
                quantQuestoesAcertadas.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.zerosete));
                break;
            case 8:
                quantQuestoesAcertadas.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.zerooito));
                break;
            case 9:
                quantQuestoesAcertadas.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.zeronove));
                break;
            case 10:
                quantQuestoesAcertadas.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.dezdez));
                break;
        }
    }

}