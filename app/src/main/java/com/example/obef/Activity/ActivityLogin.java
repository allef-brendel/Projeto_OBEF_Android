package com.example.obef.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.obef.Cadastro.CadastroActivity;
import com.example.obef.Configuracao.ConfiguracaoFirebase;
import com.example.obef.Gerenciamento.Gravador;
import com.example.obef.Helper.Base64Custom;
import com.example.obef.Helper.Preferencias;
import com.example.obef.Modelos.Aluno;
import com.example.obef.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ActivityLogin extends AppCompatActivity implements TextView.OnEditorActionListener {

    private EditText email;
    private EditText senha;
    private Button botaoLogar;
    private Button botaoCadastrese;
    private Aluno aluno;
    private FirebaseAuth autenticacao;
    private Gravador gravador;
    private ProgressDialog pdia;
    private AlphaAnimation animation;
    boolean online;


    @Override
    public void onBackPressed(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        gravador=new Gravador();
        super.onCreate(savedInstanceState);

        setarContet();
        validarUsuario();

    }

    private void validarUsuario() {
        if(!validouUserOffline()){
            cadastrar();
        }else {
            gravador.gravarArquivo("LastUser.txt","0");
            abriTelaPrincipal();
            finish();
        }
    }

    private void setarContet() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
       // if(width==1080 && height==1920){
       //     setContentView(R.layout.layout_1920_login);
       // }else{
            setContentView(R.layout.activity_tela_login);
        //}
    }

    private void cadastrar(){

        email = findViewById(R.id.editeText_login_email);
        senha = findViewById(R.id.editText_login_senha);
        botaoCadastrese = findViewById(R.id.botaoCadastrese);
        botaoLogar = findViewById(R.id.botaoLogin);

        senha.setOnEditorActionListener(this);

        botaoLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animecaoBotao(botaoLogar);
               // botoesInvisiveis();
                aluno = new Aluno();
                aluno.setEmail(email.getText().toString().toLowerCase());
                aluno.setSenha(senha.getText().toString());
                if (email.getText().toString().toLowerCase().equals("")|| senha.getText().toString().equals("")) {
                    Toast.makeText(ActivityLogin.this, "Digite e-mail e senha", Toast.LENGTH_SHORT).show();
                    //botoesVisiveis();
                } else validarLogin();
            }
        });

        botaoCadastrese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                botaoCadastrese.setEnabled(false);
                animecaoBotao(botaoCadastrese);
                //botoesInvisiveis();
                startActivity(new Intent(ActivityLogin.this,CadastroActivity.class));
                overridePendingTransition(R.anim.goup, R.anim.godown);
                finish();
            }
        });
    }

    private  void verificarUsuarioLogado(){
        if(gravador.lerArquivo("User.txt").length()>0){
            abriTelaPrincipal();
        }
    }

    private void validarLogin() {
            botaoLogar.setEnabled(false);
            autenticacao = ConfiguracaoFirebase.getFireBaseAutenticacao();
            autenticacao.signInWithEmailAndPassword(aluno.getEmail(), aluno.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        loginValidado();

                    } else {
                        botaoLogar.setEnabled(true);
                        loginNaoValidado(task);
                    }
                }
            });
        }
    public boolean validouUserOffline(){
        if(gravador.lerArquivo("LastUser.txt").length()>0 &&Integer.parseInt(gravador.lerArquivo("LastUser.txt").substring(0,1))==1){
            return false;
        }
        if(gravador.lerArquivo("User.txt").length()>1){
            return true;
        }else{
            return false;
        }
    }

    public void abriTelaPrincipal(){

        gravador.gravarArquivo("LastUser.txt","0");
        Intent intent = new Intent(ActivityLogin.this,RecuperarQuestao.class);
        startActivity(intent);
        overridePendingTransition(R.anim.goup, R.anim.goup);
        finish();
    }

    public void abrirCadastroUsuario(View view){

        Intent intent = new Intent(ActivityLogin.this, CadastroActivity.class);
        startActivity(intent);
    }
    private void botoesVisiveis(){
        botaoCadastrese.setVisibility(View.VISIBLE);
        botaoLogar.setVisibility(View.VISIBLE);

    }
    private void botoesInvisiveis(){
        botaoLogar.setVisibility(View.INVISIBLE);
        botaoCadastrese.setVisibility(View.INVISIBLE);
    }
    private void loginValidado(){
        pdia = new ProgressDialog(ActivityLogin.this);
        pdia.setMessage("Carregando...");
        pdia.show();

        DatabaseReference firebase = FirebaseDatabase.getInstance().getReference();
        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(aluno.getEmail().equals(gravador.lerUser())){
                    abriTelaPrincipal();
                }
                else{
                    setArquivosAoLogar(dataSnapshot);
                    bloqueioDesafioSeSegundoLogin();
                    abriTelaPrincipal();

                    Toast.makeText(ActivityLogin.this, "Sucesso ao logar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setArquivosAoLogar(DataSnapshot dataSnapshot) {
        Preferencias preferencias = new Preferencias(ActivityLogin.this);
        String identificadorUsuarioLogado = Base64Custom.codificarBase64(aluno.getEmail());
        preferencias.salvarDados(identificadorUsuarioLogado);
        gravador.salvarUser(aluno.getEmail());
        System.out.println("Salvou o usuário");
        gravador.resetarAtualizacaoPontos();
        gravador.resetarPontosTotais();
        gravador.resetarPontos();
        gravador.resetarMoedas();
        gravador.resetarQuantAcertos();
        gravador.resetarAtualizacaoPontos();
        gravador.recuperarMoedas();
        int pontosTotais = dataSnapshot.child("Acertos").child(identificadorUsuarioLogado).child("pontos").getValue(Integer.class);
        gravador.salvarPontosGanhosTotal(pontosTotais);
        String datalidas = dataSnapshot.child("Acertos").child(identificadorUsuarioLogado).child("ultimoDiaLogado").getValue(String.class);
        gravador.salvarData(datalidas);

    }

    private void loginNaoValidado(@NonNull Task<AuthResult> task){
        try {
            throw task.getException();
        } catch (FirebaseAuthInvalidUserException e) {
            email.setError("E-mail não cadastrado");
        } catch (FirebaseAuthInvalidCredentialsException e) {
            senha.setError("E-mail ou senha invalidos");
        } catch (Exception e) {
            Toast.makeText(ActivityLogin.this, "Erro ao efetuar o login. Verifique sua conexão com a internet.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
       // botaoLogar.setVisibility(View.VISIBLE);
       // botaoCadastrese.setVisibility(View.VISIBLE);

    }
    private void bloqueioDesafioSeSegundoLogin(){
        if (gravador.lerData().equals("0")) {

        } else if (gravador.compararData(gravador.lerData())) {
            gravador.bloquearDesafio();
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
          //  botoesInvisiveis();
            aluno = new Aluno();
            aluno.setEmail(email.getText().toString().toLowerCase());
            aluno.setSenha(senha.getText().toString());
            if (email.getText().toString().toLowerCase().equals("") || senha.getText().toString().equals("")) {
                Toast.makeText(ActivityLogin.this, "Digite e-mail e senha", Toast.LENGTH_SHORT).show();
                botaoLogar.setEnabled(true);
             //   botoesVisiveis();
            } else validarLogin();
        }
        return false;
    }

    public void animecaoBotao(Button button){
        animation = new AlphaAnimation(1, 0); // Altera alpha de visível a invisível
        animation.setDuration(200);
        animation.setInterpolator(new LinearInterpolator());
        button.startAnimation(animation);
    }

}