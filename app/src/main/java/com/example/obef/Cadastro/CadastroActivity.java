package com.example.obef.Cadastro;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.obef.Activity.ActivityLogin;
import com.example.obef.Configuracao.ConfiguracaoFirebase;
import com.example.obef.Helper.Base64Custom;
import com.example.obef.Helper.Preferencias;
import com.example.obef.Modelos.Aluno;
import com.example.obef.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;


public class CadastroActivity extends AppCompatActivity {

    private EditText cadastroEmail;
    private EditText cadastroSenha;
    private EditText cadastroNome;
    private Button botaoCadastra;
    private RadioGroup radioGroupSexo;
    private RadioButton radioButtonEscolhido;
    private Aluno aluno;
    private FirebaseAuth autenticacao;
    private Spinner combobox;

    @Override
    public void onBackPressed(){
        startActivity(new Intent(this,ActivityLogin.class));
        overridePendingTransition(R.anim.goup, R.anim.godown);
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
            setContentView(R.layout.layout_1920_cadastro);
        }else{
            setContentView(R.layout.activity_tela_cadastro);
        }


        cadastroEmail = findViewById(R.id.editeText_cadastro_email);
        cadastroNome = findViewById(R.id.editeText_cadastro_nome);
        cadastroSenha = findViewById(R.id.editeText_cadastro_senha);
        radioGroupSexo = findViewById(R.id.radiogroupSexo);
        radioButtonEscolhido = findViewById(R.id.radioFeminino);
        botaoCadastra = findViewById(R.id.botaoCadastro2);

        combobox = findViewById(R.id.combobox);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.combobox, android.R.layout.simple_spinner_item);
        combobox.setAdapter(adapter);

        botaoCadastra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                aluno = new Aluno();
                aluno.setEmail(cadastroEmail.getText().toString().toLowerCase());
                aluno.setSenha(cadastroSenha.getText().toString());
                aluno.setNome(cadastroNome.getText().toString());
                if(cadastroEmail.getText().toString().toLowerCase().equals("") ||
                        cadastroSenha.getText().toString().equals("") || cadastroNome.getText().toString().equals("")){
                    Toast.makeText(CadastroActivity.this,"Você não informou todos os dados", Toast.LENGTH_SHORT).show();
                }else cadastrarUsuario();
            }
        });
    }

    public void cadastrarUsuario(){

        autenticacao = ConfiguracaoFirebase.getFireBaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(aluno.getEmail(),aluno.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Toast.makeText(CadastroActivity.this, "Conta cadastrada com sucesso!", Toast.LENGTH_SHORT).show();
                    FirebaseUser firebaseUser = task.getResult().getUser();

                    String identificadorUsuario = Base64Custom.codificarBase64(aluno.getEmail());
                    aluno.setId(identificadorUsuario);
                    aluno.salvar();

                    Preferencias preferencias = new Preferencias(CadastroActivity.this);
                    preferencias.salvarDados(identificadorUsuario);

                    abrirLoginUsuario();
                    finish();
                }else{
                    String erroExecao = "";

                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        erroExecao = "Erro ao cadastrar. Digite uma senha com no minimo 6 caracteres, contendo letras e números";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExecao = "O e-mail digitado é invalido, digite um novo e-mail";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erroExecao = "Já existe uma conta criada com esse e-mail";
                    } catch (Exception e) {
                        erroExecao = "Erro ao efetuar cadastro";
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroActivity.this, erroExecao, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void abrirLoginUsuario(){
        Intent intent = new Intent(CadastroActivity.this, ActivityLogin.class);
        startActivity(intent);
        overridePendingTransition(R.anim.goup, R.anim.godown);
        finish();
    }
}