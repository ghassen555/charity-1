package com.loukil.Contactini;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class connexion extends AppCompatActivity implements View.OnClickListener {
    private TextView mdpoublie ;
    private TextView creecompte , emailedit , passwordedit ;
    private Button connexionbtn ;
    private FirebaseAuth mauth ;
    private String email ;
    private String password ;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
    mdpoublie = findViewById(R.id.mdpoublieconnexion);
    creecompte = findViewById(R.id.creecompteconnexion);
    connexionbtn=findViewById(R.id.connectionconnecxionbtn);
    emailedit=findViewById(R.id.Emailconnexion);
    passwordedit=findViewById(R.id.passwordconnexion);
        mauth=FirebaseAuth.getInstance();
    progressBar=findViewById(R.id.pgcnx);
    mdpoublie.setOnClickListener(this);
    creecompte.setOnClickListener(this) ;
    connexionbtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v==mdpoublie) {
            Intent goingresetmdp = new Intent(connexion.this, reinitialisermotdepasse.class);
            startActivity(goingresetmdp);
        }
        if(v==creecompte){
            Intent goingtoregister = new Intent(connexion.this,inscriptionpro.class) ;
            startActivity(goingtoregister);
        }
        if (v==connexionbtn){
            email = emailedit.getText().toString().trim();
            password = passwordedit.getText().toString().trim();
            if(verif(email,password)){
                progressBar.setVisibility(View.VISIBLE);
                mauth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful())
                                {

                                    Intent goingToMain = new Intent(connexion.this,TESTTEST.class);
                                    goingToMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(goingToMain);
                                } else {
                                    Toast.makeText(connexion.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
     
        }
    }

    private boolean verif(String email, String password) {
        if (email.length()==0){
            emailedit.setError("Veuillez saisir votre email");
            return false ;
        }
        if (password.length()==0){
            passwordedit.setError("Veuillez saisir votre Mot de passe");
            return false ;
        }
        return true ;

    }
}
