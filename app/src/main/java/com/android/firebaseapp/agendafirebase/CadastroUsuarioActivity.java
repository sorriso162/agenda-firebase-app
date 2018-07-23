package com.android.firebaseapp.agendafirebase;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText email;
    private EditText senha;
    private Button   cadastrar;
    private Button   cancelar;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference usuarioReferencia = database.child("usuario");
    private String senhaDigitada;
    private String emailDigitado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        firebaseAuth = FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.editTextCadCliEmailID);
        senha = (EditText) findViewById(R.id.editTextCadCliSenhaID);
        cadastrar = (Button) findViewById(R.id.buttonCadCliID);
        cancelar  = (Button) findViewById(R.id.buttonCadCliCancelaID);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailDigitado = email.getText().toString();
                senhaDigitada = senha.getText().toString();
                if(email.getText().length() == 0){
                    email.setError("digite o email!");
                }else if(senha.getText().length() == 0){
                    senha.setError("digite a senha!");
                }else{
                firebaseAuth.createUserWithEmailAndPassword(emailDigitado,senhaDigitada)
                        .addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            firebaseAuth.getCurrentUser();
                            String UID = firebaseAuth.getUid();
                            Usuario us = new Usuario();
                            us.setEmail(emailDigitado);
                            usuarioReferencia.child(UID).setValue(us);
                            Toast.makeText(getApplicationContext(),"Cadastrado realizado com sucesso", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"Não foi possivel realizar o cadastro", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                }
            }
        });
        //------------------------------------------------------------
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(CadastroUsuarioActivity.this, MainActivity.class);
                startActivity(it);
            }
        });
    }


}
