package com.example.obef.Cadastro;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.LinearInterpolator;
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
    private AlphaAnimation animation;

    @Override
    public void onBackPressed(){
        startActivity(new Intent(this,ActivityLogin.class));
        overridePendingTransition(R.anim.goup, R.anim.godown);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);

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
                animacaoBotao(botaoCadastra);

                aluno = new Aluno();
                aluno.setEmail(cadastroEmail.getText().toString().toLowerCase());
                aluno.setSenha(cadastroSenha.getText().toString());
                aluno.setNome(cadastroNome.getText().toString());
                aluno.setSerie(""+combobox.getSelectedItemPosition());
                if(cadastroEmail.getText().toString().toLowerCase().equals("") ||
                        cadastroSenha.getText().toString().equals("") || cadastroNome.getText().toString().equals("")
                        || combobox.getSelectedItemPosition()==0){
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
                    botaoCadastra.setEnabled(false);

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
                    botaoCadastra.setEnabled(true);
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        cadastroSenha.setError("Digite uma senha com no minimo 6 caracteres");
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        cadastroEmail.setError("O e-mail digitado é invalido, digite um novo e-mail");
                    } catch (FirebaseAuthUserCollisionException e) {
                        cadastroEmail.setError("Já existe uma conta criada com esse e-mail");
                    } catch (Exception e) {
                        Toast.makeText(CadastroActivity.this, "Erro ao efetuar cadastro. Verifique sua conexão com a internet", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
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

    public void animacaoBotao(Button button){
        animation = new AlphaAnimation(1, 0); // Altera alpha de visível a invisível
        animation.setDuration(200);
        animation.setInterpolator(new LinearInterpolator());
        button.startAnimation(animation);
    }

}