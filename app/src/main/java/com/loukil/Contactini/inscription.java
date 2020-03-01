package com.loukil.Contactini;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;

import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class inscription extends AppCompatActivity {

    private static final String TAG = "inscription";

    private EditText editemail, editpassword, editname;

    private ProgressBar pb;

    private Button finishbtn;

    private FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        mauth = FirebaseAuth.getInstance();
        finishbtn = findViewById(R.id.finishinscriptionbutton);
        editemail = findViewById(R.id.inscriptionEmail);
        editpassword = findViewById(R.id.inscriptionpassword);
        editname = findViewById(R.id.inscriptionnom);
        pb=findViewById(R.id.inscclientpb);

        finishbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = editemail.getText().toString().trim();
                String password = editpassword.getText().toString().trim();
                final String name = editname.getText().toString().trim();
                if (verif(email , password , name)){
                    pb.setVisibility(View.VISIBLE);
                    mauth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                users user = new users(name, email);
                                FirebaseDatabase.getInstance().getReference().child("client").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            pb.setVisibility(View.GONE);
                                            Toast.makeText(inscription.this,"Inscription réussite",Toast.LENGTH_SHORT).show();
                                            Intent goingToMain = new Intent(inscription.this,TESTTEST.class);
                                            goingToMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(goingToMain);
                                        }
                                        else{
                                            Toast.makeText(inscription.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            } else{
                                Toast.makeText(inscription.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                }



                }

            private boolean verif(String email, String password, String name) {
                if (name.length()==0){
                    editname.setError("Veuillez saisir votre nom et prénom");
                    return false ;
                }
                if (email.length()==0){
                    editemail.setError("Veuillez saisir votre email");
                    return false ;
                }
                if (password.length()==0){
                    editpassword.setError("Veuillez saisir votre Mot de passe");
                    return false ;
                }

                return true ;
            }
        });

        }
    }