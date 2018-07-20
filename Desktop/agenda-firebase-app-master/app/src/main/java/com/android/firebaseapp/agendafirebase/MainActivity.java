package com.android.firebaseapp.agendafirebase;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements  GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private EditText email;
    private EditText senha;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference usuarioReferencia = database.child("usuario");
    private SignInButton botaoGoogle;
    private GoogleApiClient googleApiClient;
    private GoogleSignInClient mGoogleSignInClientGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private String emailUser;
    private String senhaUser;
    private Context context = this;
    private Button logar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(MainActivity.this.getResources().getString(R.string.clientID))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(context)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        googleApiClient.connect();
        firebaseAuth = FirebaseAuth.getInstance();
        email = (EditText) findViewById(R.id.editTextEmailID);
        senha = (EditText) findViewById(R.id.editTextSenhaID);
        logar = (Button)   findViewById(R.id.entrarID);
        botaoGoogle = (SignInButton) findViewById(R.id.botaoGoogleID);

        //Eventos dos Botoes
        onStart();
        botaoGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.botaoGoogleID:
                        signIn();
                        break;
                }
            }
        });

        logar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                emailUser = email.getText().toString();
                senhaUser = senha.getText().toString();
                if(email.getText().length() == 0){
                    email.setError("digite o email!");
                }else if(senha.getText().length() == 0){
                    senha.setError("digite a senha!");
                }else{
/*
                    firebaseAuth.signInWithEmailAndPassword(emailUser,senhaUser).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                               String UID = firebaseAuth.getCurrentUser().getUid();
                               FirebaseUser user = firebaseAuth.getCurrentUser();
                                if(user != null){
                               Intent it = new Intent(MainActivity.this, MenuActivity.class);
                              String emailUID = emailUser;
                               it.putExtra("userUid",UID);
                               it.putExtra("email",emailUID);
                               startActivity(it);}
                        }else{Toast.makeText(getApplicationContext(),"não foi possivel realizar o login", Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
            }}
        });
    }
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                Log.i("TOKEN", "Gmail:"+account.getEmail()+"  nome: "+account.getDisplayName()+"  Token: "+account.getIdToken());
                firebaseAuthWithGoogle(account);
            } else {
                Toast.makeText(getApplicationContext(), "Falha ao logar", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        Log.d("conta", "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                          String email = acct.getEmail();
                          String nome = acct.getDisplayName();
                          String sobreNome = acct.getFamilyName();
                          String token = acct.getIdToken();
                            String UID = user.getUid();
                            usuarioReferencia.child(UID).child("email").setValue(email);
                            usuarioReferencia.child(UID).child("nome").setValue(nome);
                            usuarioReferencia.child(UID).child("sobreNome").setValue(sobreNome);
                            usuarioReferencia.child(UID).child("token").setValue(token);
                            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                            intent.putExtra("userUid",UID);
                            intent.putExtra("email",email);
                           startActivity(intent);
                        } else {
                            Log.w("", "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    @Override
    public void onConnected(@android.support.annotation.Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
