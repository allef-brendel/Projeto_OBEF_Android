package com.example.obef.Activity.DesafioDoDia;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
    private Dialog MyDialog;

    @Override
    public void onBackPressed(){
        startActivity(new Intent(desafio, MenuQuestoes.class));
        overridePendingTransition(R.anim.goup, R.anim.godown);
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
        setContentView(R.layout.activity_tela_desafiododia);
        setarComponentes();
        desafio=this;
        auth= ConfiguracaoFirebase.getFireBaseAutenticacao();
        firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("Acertos").child(Base64Custom.codificarBase64(auth.getCurrentUser().getEmail()));
        setClickButtonDica();
        setClickButtonEscolher();
        setarTextoNoLayoutDaQuestao();
    }

    private void setarTextoNoLayoutDaQuestao() {
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

    private void setClickButtonEscolher() {
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
    }

    private void setClickButtonDica() {
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
    }

    private void setarComponentes() {
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
        MyDialog = new Dialog(Desafio.this);
        MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyDialog.setContentView(R.layout.alert_dialog_modificado);
        MyDialog.setCancelable(false);
        MyDialog.setTitle("Resposta Errada");

        Button hello = MyDialog.findViewById(R.id.hello);
        TextView text = MyDialog.findViewById(R.id.textViewDialog);
        TextView textMeio = MyDialog.findViewById(R.id.textMeio);
        ImageView image = MyDialog.findViewById(R.id.imageViewDialog);

        textMeio.setText("Justificativa");
        text.setText(gravador.recuperaStatusErrou());
        image.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.porcotriste));

        hello.setEnabled(true);

        hello.setText("                Menu de Questões             ");

        hello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Desafio.this, MenuQuestoes.class));
                overridePendingTransition(R.anim.goup, R.anim.godown);
                finish();
            }
        });

        MyDialog.show();
    }

    private void respostaCerta() {
        gravador.salvarPontosGanhos(pontosGanhos);
        gravador.salvarPontosGanhosTotal(pontosGanhos);

        MyDialog = new Dialog(Desafio.this);
        MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyDialog.setContentView(R.layout.alert_dialog_modificado);
        MyDialog.setCancelable(false);
        MyDialog.setTitle("Resposta Certa");

        Button hello = MyDialog.findViewById(R.id.hello);
        TextView text = MyDialog.findViewById(R.id.textViewDialog);
        TextView textMeio = MyDialog.findViewById(R.id.textMeio);
        ImageView image = MyDialog.findViewById(R.id.imageViewDialog);

        textMeio.setText("Pontos");
        text.setText(gravador.recuperaStatusAcertou());
        image.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.porcofeliz));

        hello.setEnabled(true);

        hello.setText("                     Menu de Questões                  ");

        hello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Desafio.this, MenuQuestoes.class));
                overridePendingTransition(R.anim.goup, R.anim.godown);
                finish();
            }
        });

        MyDialog.show();

    }
    public void dicaUsada(){
        this.usouDica=true;
        pontosGanhos=80;
    }
    public abstract int gerarID();






}