package com.android.firebaseapp.agendafirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DiasCadastroActivity extends AppCompatActivity {
    private Spinner selecao1;
    private Spinner selecao2;
    private Spinner selecao3;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference usaurioDataBase = database.child("usuario");
    private Button saveDias;
    private String [] opcoes = {"segunda","terca","quarta","quinta","sexta","sabado"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dias_cadastro);

        selecao1 = (Spinner) findViewById(R.id.diaSelectOneID);
        selecao2 = (Spinner) findViewById(R.id.diaSelectTwoID);
        selecao3 = (Spinner) findViewById(R.id.diaSelecTreeID);
        saveDias = (Button) findViewById(R.id.saveDayButtomID);

        ArrayAdapter arrayAdapter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                opcoes);

        selecao1.setAdapter(arrayAdapter);
        selecao2.setAdapter(arrayAdapter);
        selecao3.setAdapter(arrayAdapter);
        saveDias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(selecao1.getSelectedItem() == selecao2.getSelectedItem()){
                    Toast.makeText(getApplicationContext(),"Não pode haver duas opçoes iguais", Toast.LENGTH_SHORT).show();
                }else if(selecao2.getSelectedItem() == selecao3.getSelectedItem()){
                    Toast.makeText(getApplicationContext(),"Não pode haver duas opçoes iguais", Toast.LENGTH_SHORT).show();
                }else if(selecao1.getSelectedItem() == selecao3.getSelectedItem()){
                    Toast.makeText(getApplicationContext(),"Não pode haver duas opçoes iguais", Toast.LENGTH_SHORT).show();
                }else{
                    String UID = getIntent().getStringExtra("userUid");
                    usaurioDataBase.child(UID).child("dia1").setValue(selecao1.getSelectedItem().toString());
                    usaurioDataBase.child(UID).child("dia2").setValue(selecao2.getSelectedItem().toString());
                    usaurioDataBase.child(UID).child("dia3").setValue(selecao3.getSelectedItem().toString());
                    Toast.makeText(getApplicationContext(),"Dias foram registrados", Toast.LENGTH_SHORT).show();
                }
                }
            });
        }
    }

