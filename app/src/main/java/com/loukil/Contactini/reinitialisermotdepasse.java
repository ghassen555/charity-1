package com.loukil.Contactini;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class reinitialisermotdepasse extends AppCompatActivity {
private EditText editemail ;
private Button resetbtn ;
private FirebaseAuth mauth ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reinitialisermotdepasse);
        editemail = findViewById(R.id.Emailresetpassword);
        resetbtn = findViewById(R.id.resetpasswordbtn);
        mauth=FirebaseAuth.getInstance();
        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail = editemail.getText().toString().trim();
                if (TextUtils.isEmpty(useremail))
                {
                    Toast.makeText(reinitialisermotdepasse.this,"Veuillez saisir votre Email",Toast.LENGTH_SHORT).show();
                }else {
                    mauth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                       if (task.isSuccessful())
                       {
                           Toast.makeText(reinitialisermotdepasse.this,"Veuillez vérifier votre Courier electronique",Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(reinitialisermotdepasse.this,connexion.class);
                           startActivity(intent);

                       }else {
                           Toast.makeText(reinitialisermotdepasse.this,task.getException().getMessage().toString(),Toast.LENGTH_SHORT).show();
                       }
                        }
                    });
                }
            }
        });
    }
}
