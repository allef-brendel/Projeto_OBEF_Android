package com.example.obef.AcertouErrou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.obef.Activity.MenuQuestoes;
import com.example.obef.Gerenciamento.Gravador;
import com.example.obef.R;

public class Errou extends AppCompatActivity {

    private TextView justificativa;
    private Gravador gravador;
    private Button menuquestoes;
    @Override
    public void onBackPressed(){
        startActivity(new Intent(Errou.this, MenuQuestoes.class));
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
            setContentView(R.layout.layout_1920_porcotriste);
        }else{
            setContentView(R.layout.activity_porcotriste);
        }

        gravador=new Gravador();

        justificativa = findViewById(R.id.quantpontos);
        menuquestoes = findViewById(R.id.botaoquestoes);

        justificativa.setText(gravador.recuperaStatusErrou());

        menuquestoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Errou.this, MenuQuestoes.class));
                finish();
            }
        });
    }
}
