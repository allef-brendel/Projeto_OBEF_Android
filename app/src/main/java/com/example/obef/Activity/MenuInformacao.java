package com.example.obef.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.obef.R;

public class MenuInformacao extends AppCompatActivity {

    private Button botaoOK;
    @Override
    public void onBackPressed(){
        startActivity(new Intent(this,Menu.class));
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        if (width == 1080 && height == 1920) {
            setContentView(R.layout.layout_1920_informacoes);
        } else {
            setContentView(R.layout.activity_tela_informacoes);
        }
    }
}
