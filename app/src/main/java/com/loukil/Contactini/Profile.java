package com.loukil.Contactini;

import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Profile extends AppCompatActivity {
    private DatabaseReference firebaseDatabase, databasefavourite;
    private String profileid, currentUserId;
    private TextView nom, adress, profession, tel, bio;
    private ImageView profimg;
    private RatingBar ratingprofile, avgratingprofile;
    private Button favouritebtn;
    private ProgressBar progressBar;
    String imglink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        nom = findViewById(R.id.nameview);
        progressBar=findViewById(R.id.profilepb);
        adress = findViewById(R.id.Regionview);
        favouritebtn = findViewById(R.id.favouritebtn);
        profession = findViewById(R.id.profesview);
        tel = findViewById(R.id.numtelview);
        bio = findViewById(R.id.textViewbio);
        bio.setMovementMethod(new ScrollingMovementMethod());
        profimg = findViewById(R.id.profileimage);
        ratingprofile = findViewById(R.id.profilerating);
        avgratingprofile = findViewById(R.id.avgratingbar);
        progressBar.setVisibility(View.VISIBLE);


        if (getIntent().hasExtra("pi")) {
            profileid = getIntent().getStringExtra("pi");
            firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("association").child(profileid);
            firebaseDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    progressBar.setVisibility(View.GONE);
                    nom.setText(dataSnapshot.child("nom").getValue().toString());


                    adress.setText(dataSnapshot.child("adres").getValue().toString());
                    profession.setText(dataSnapshot.child("category").getValue().toString());
                    tel.setText(dataSnapshot.child("tel").getValue().toString());
                    if (dataSnapshot.child("bio").getValue().toString().equals("")) {
                        bio.setText("Informations Supplimentaires...");
                    }
                    else {
                        bio.setText(dataSnapshot.child("bio").getValue().toString());
                    }
                    if (dataSnapshot.hasChild("asslogo")) {
                         imglink = dataSnapshot.child("asslogo").getValue().toString();
                        Glide.with(getApplicationContext()).load(imglink).placeholder(R.drawable.face).apply(RequestOptions.circleCropTransform()).into(profimg);
                    }


                    avgratingprofile.setRating(Float.parseFloat(dataSnapshot.child("rating").getValue().toString()));
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }

    }
}
