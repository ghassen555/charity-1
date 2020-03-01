package com.loukil.Contactini;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class signup extends AppCompatActivity implements View.OnClickListener {
    private Button signupprobutton;
    private Button signupclientbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signupclientbutton = findViewById(R.id.signupclientbutton);
        signupprobutton = findViewById(R.id.signupprobutton);
        signupclientbutton.setOnClickListener(this);
        signupprobutton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v==signupclientbutton)
        {
            Intent goingtoinscription = new Intent(signup.this,inscription.class);
            startActivity(goingtoinscription);
        }
        if (v==signupprobutton)
        {
            Intent goingtoproinscription = new Intent(signup.this,inscriptionpro.class);
            startActivity(goingtoproinscription);
        }

    }
}
