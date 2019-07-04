package com.example.obef.Splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.example.obef.Activity.ActivityLogin;
import com.example.obef.R;

public class Splash2 extends AppCompatActivity {

    private Button botaoPlay;
    @Override
    public void onBackPressed(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        if(width==1080 && height==1920){
            setContentView(R.layout.layout_1920_play);
        }else{
            setContentView(R.layout.activity_tela_splash2);
        }


        botaoPlay = findViewById(R.id.botaoPlay);

        botaoPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Splash2.this, ActivityLogin.class));
                overridePendingTransition(R.anim.goup, R.anim.goup);
                finish();
            }
        });
    }
}
