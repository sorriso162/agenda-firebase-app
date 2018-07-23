package com.android.firebaseapp.agendafirebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuActivity extends AppCompatActivity {
    private Button escolherDias;
    private ImageView calendarioImg;
    private Button infoAd;
    private TextView textoIDU;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference usuarioReference  = databaseReference.child("usuario");
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        textoIDU = (TextView) findViewById(R.id.meuNomeID);
        infoAd = (Button) findViewById(R.id.adicionalInfID);
        escolherDias = (Button) findViewById(R.id.escolherDiasDeAula);
        calendarioImg = (ImageView) findViewById(R.id.imageViewCalenderID);

        calendarioImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, CalendarioView.class);
                String emailUID = getIntent().getStringExtra("email");
                String UID = getIntent().getStringExtra("userUid");
                intent.putExtra("userUid",UID);
                startActivity(intent);

            }
        });
        escolherDias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, DiasCadastroActivity.class);
                String UID = getIntent().getStringExtra("userUid");
                intent.putExtra("userUid",UID);
                startActivity(intent);
            }
        });
        infoAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MenuActivity.this, informacoesAdicionais.class);
                String UID = getIntent().getStringExtra("userUid");
                it.putExtra("userUid",UID);
                startActivity(it);
            }
        });
    }
}
