package com.android.firebaseapp.agendafirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class informacoesAdicionais extends AppCompatActivity {

    private EditText nome;
    private EditText sobreNome;
    private EditText idade;
    private Button   salvar;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference usaurioDataBase = database.child("usuario");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacoes_adicionais);

        nome      = (EditText) findViewById(R.id.nomeTextCliID);
        sobreNome = (EditText) findViewById(R.id.sobrenomeTextID);
        idade     = (EditText) findViewById(R.id.idadeTextID);
        salvar    = (Button)   findViewById(R.id.salvarInfAdID);

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nome.getText().length() == 0){
                    nome.setError("Você deve preencher o campo nome");
                }else if(sobreNome.getText().length() == 0){
                    sobreNome.setError("Você deve preencher o campo sobrenome");
                }else if(idade.getText().length() == 0){
                    idade.setError("Você preencher o campo idade");
                }else{
                    String UID = getIntent().getStringExtra("userUid");
                    usaurioDataBase.child(UID).child("nome").setValue(nome.getText().toString());
                    usaurioDataBase.child(UID).child("sobreNome").setValue(sobreNome.getText().toString());
                    usaurioDataBase.child(UID).child("idade").setValue(idade.getText().toString());
                    Toast.makeText(getApplicationContext(),"cadastrado com sucesso", Toast.LENGTH_SHORT).show();}
            }
        });
    }
}