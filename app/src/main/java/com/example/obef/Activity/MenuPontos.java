package com.example.obef.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.obef.Adapter.AbasAdapter;
import com.example.obef.Gerenciamento.Gravador;
import com.example.obef.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MenuPontos extends AppCompatActivity {

    private String[] itens = {"","",""};
    private String[] itens2 = {"","",""};

    public ListView listView;
    public ListView listView2;
    public TextView viewPontos;
    public TextView viewPontosTotais;
    private Gravador gravador;
    DatabaseReference firebase;
    private ProgressDialog pdia;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    public void onBackPressed(){
        startActivity(new Intent(this,Menu.class));
        overridePendingTransition(R.anim.goup, R.anim.godown);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        pdia = new ProgressDialog(MenuPontos.this);
        pdia.setMessage("Carregando...");
        pdia.show();

        gravador=new Gravador();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pontos);

        firebase= FirebaseDatabase.getInstance().getReference();

        //Query que ordena os acertos de todos os alunos cadastrados do maior para o menor.
        Query query=firebase.child("Acertos").orderByChild("pontos");



        //Primeiro listener para pegar os pontos e ids dos alunos referentes aos pontos.
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<Integer> pontos=new ArrayList<Integer>();
                final List<String> ids=new ArrayList<String>();
                for(DataSnapshot d:dataSnapshot.getChildren()){
                    ids.add(d.getKey());
                    pontos.add(d.child("pontos").getValue(Integer.class));
                }
                /*
                Segundo Listener que pega o id da escola dos alunos e seu nome, assim como também
                pega o nome do aluno para então mostrar o rank por pontos
                */
                firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int cont=0;
                        for(int x=pontos.size()-1;x>=0;x--){
                            String idEscola=""+dataSnapshot.child("Alunos").child(ids.get(x)).child("idEscola").getValue(Integer.class).toString();
                            String nomeAluno=dataSnapshot.child("Alunos").child(ids.get(x)).child("nome").getValue(String.class);
                            String nomeEscola =dataSnapshot.child("Escolas").child(idEscola).child("nome").getValue(String.class);
                            if(cont<3){
                                itens[cont]=nomeAluno;
                                itens2[cont]=""+pontos.get(x);
                                cont++;

                            }
                        }

                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, android.R.id.text1, itens2);
                        //listView2.setAdapter(adapter2);

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, android.R.id.text1, itens);
                       // listView.setAdapter(adapter);

                        pdia.cancel();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        viewPontos = findViewById(R.id.viewPontos);
        viewPontosTotais = findViewById(R.id.viewPontosTotais);
        listView = findViewById(R.id.listViewPontosTotais);
        listView2 = findViewById(R.id.listViewPontos);

        mTabLayout = findViewById(R.id.abas);
        mViewPager = findViewById(R.id.abas_view_pager);

        mViewPager.setAdapter(new AbasAdapter(getSupportFragmentManager(),getResources().getStringArray(R.array.tabs)));
        mTabLayout.setupWithViewPager(mViewPager);

    }
}

