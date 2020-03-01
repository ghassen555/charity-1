package com.loukil.Contactini;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class inscriptionpro extends AppCompatActivity {
    private FirebaseAuth mauth;
    private DatabaseReference locationdbref ;
    private Button finishprobtn;
    private CircleImageView profileimageview;
    private EditText editnom, editemail, edittel, editbio, editpassword , editadr;
    Uri uriprofileimage;
    StorageReference deletepicref ;
    private ProgressDialog pd ;
private ProgressBar pb;
    String profileimageurl=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscriptionpro);
        mauth = FirebaseAuth.getInstance();
        finishprobtn = findViewById(R.id.profinishbutton);
        editbio = findViewById(R.id.editbioprof);
        editadr= findViewById(R.id.editadrr);
        editemail = findViewById(R.id.EmailPro);
        edittel = findViewById(R.id.TelPro);
        editnom = findViewById(R.id.nompro);
        editpassword = findViewById(R.id.propassword);
        profileimageview = findViewById(R.id.imageViewProfilePro);
        pb=findViewById(R.id.inscpropb);

        pd=new ProgressDialog(inscriptionpro.this);
        pd.setTitle("Telechargement");
        pd.setMessage("Connexion en cours");
        pd.setCanceledOnTouchOutside(false);




        profileimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileimageurl!=null)
                {
                    deletepicref = FirebaseStorage.getInstance().getReferenceFromUrl(profileimageurl);
                    deletepicref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(inscriptionpro.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            showimageChooser();
                        }
                    });

                }
                else {
                    showimageChooser();
                }

            }
        });



        finishprobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password = editpassword.getText().toString().trim();
                final String email = editemail.getText().toString().trim();
                final String tel = edittel.getText().toString().trim();
                final String nom = editnom.getText().toString().trim();
                final String bio = editbio.getText().toString().trim();
                final String category;
                final String adres = editadr.getText().toString().trim();

                final String profilepic = profileimageurl;

                if (verif(email , tel , nom , password))
                {
                    pb.setVisibility(View.VISIBLE);
                    mauth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                final String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                locationdbref = FirebaseDatabase.getInstance().getReference().child("users");
                                gooder good = new gooder(nom,tel,adres,bio,email,id);
                                FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(good).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            profileimageurl=null;
                                            pb.setVisibility(View.GONE);
                                            Toast.makeText(inscriptionpro.this, "Inscription réussite", Toast.LENGTH_SHORT).show();
                                            Intent goingToMain = new Intent(inscriptionpro.this,TESTTEST.class);
                                            goingToMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(goingToMain);
                                        } else {
                                            Toast.makeText(inscriptionpro.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                            }

                        }
                    });
                }



            }

            private boolean verif(String email, String tel, String nom, String password) {
                if (nom.length
                        ()==0){
                    editnom.setError("Veuillez saisir votre nom et prénom");
                    return false ;
                }
                if (tel.length()!=8){
                    edittel.setError("Veuillez saisir votre numéro de teléphone composé de 8 chiffres");
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



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriprofileimage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), uriprofileimage);

                profileimageview.setImageBitmap(bitmap);
                uploadImageToFirebaseStorage(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImageToFirebaseStorage(Bitmap bitmap) {
        final StorageReference profileimageref = FirebaseStorage.getInstance().getReference("profilePic/" + System.currentTimeMillis() + ".jpg");
        if (uriprofileimage != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            byte[] data = baos.toByteArray();
            UploadTask uploadTask = profileimageref.putBytes(data);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    profileimageref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            profileimageurl = uri.toString();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(inscriptionpro.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    pd.show();
                    finishprobtn.setEnabled(false);
                }
            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    pd.dismiss();
                    finishprobtn.setEnabled(true);

                }
            });
        }
    }


    private void showimageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent, "Select Profile image"), 1);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (profileimageurl!=null)
        {
            deletepicref = FirebaseStorage.getInstance().getReferenceFromUrl(profileimageurl);
            deletepicref.delete();
        }

    }
}
