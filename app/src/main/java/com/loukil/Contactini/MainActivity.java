package com.loukil.Contactini;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    private RecyclerView mrecyclerview;
    private category mdataset[] = {
            new category("Pauvresse",R.drawable.pquv),
            new category("Sante",R.drawable.sante),
            new category("education",R.drawable.educ),
            new category("Faim",R.drawable.faim)
    };


    private RecyclerView.LayoutManager mlayoutmanager;
    private RecyclerView.Adapter madapter;
    private Button signupbutton;
    private Button signinbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mrecyclerview = findViewById(R.id.recyclerview);
        mlayoutmanager = new GridLayoutManager(this,2);
        mrecyclerview.setHasFixedSize(true);
        mrecyclerview.setLayoutManager(mlayoutmanager);
        madapter = new Mainadapter(mdataset, this);
        mrecyclerview.setAdapter(madapter);
        signupbutton = findViewById(R.id.signupbutton);
        signinbutton = findViewById(R.id.signinbutton);
        signupbutton.setOnClickListener(this);
        signinbutton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {



        if(v==signinbutton)
        {
            Intent goingtoconnexion = new Intent(MainActivity.this,connexion.class);
            startActivity(goingtoconnexion);
        }
        if (v==signupbutton)
        {
            Intent goingtoproinscription = new Intent(MainActivity.this,inscriptionpro.class);
            startActivity(goingtoproinscription);
        }


    }

    @Override
    public void onBackPressed() {

    }
}
