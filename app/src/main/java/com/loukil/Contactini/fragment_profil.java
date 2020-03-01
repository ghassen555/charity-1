package com.loukil.Contactini;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class fragment_profil extends Fragment {
    private DatabaseReference firebaseDatabase ,updateref ;
    private EditText name , tel , bio ;
    private Button update ;
    private ImageView pic ;
    private Boolean bool ;
    private NavigationView navigation ;

    Uri uriprofileimage;
    private ProgressDialog pd ;
    String profileimageurl=null , oldpicurl=null;
    StorageReference deletepicref ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_mon_profil,null);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = view.findViewById(R.id.editnameprof);
        tel = view.findViewById(R.id.edittelprof);
        bio =view.findViewById(R.id.editbioprof);
        navigation = getActivity().findViewById(R.id.nav_view);

        pic = view.findViewById(R.id.editpicprof);
        update = view.findViewById(R.id.updatebtn);
        pd=new ProgressDialog(getActivity());
        pd.setTitle("Telechargement");
        pd.setMessage("Connexion en cours");
        pd.setCanceledOnTouchOutside(false);


        readData(new FirebaseCallback() {
            @Override
            public void onCallback(final Boolean b) {
                if (b==true)
                {
                    FirebaseDatabase.getInstance().getReference().child("pros").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (isAdded()) {
                                if (dataSnapshot.hasChild("u")) {
                                    name.setText(dataSnapshot.child("u").getValue().toString());
                                }
                                if (dataSnapshot.hasChild("t")) {
                                    tel.setText(dataSnapshot.child("t").getValue().toString());
                                }
                                if (dataSnapshot.hasChild("t")) {
                                    bio.setText(dataSnapshot.child("b").getValue().toString());
                                }
                                if (dataSnapshot.hasChild("pp")) {
                                    Glide.with(getActivity()).load(dataSnapshot.child("pp").getValue().toString())
                                            .placeholder(R.drawable.face).apply(RequestOptions.circleCropTransform()).into(pic);
                                    oldpicurl = dataSnapshot.child("pp").getValue().toString();

                                }
                            pd.dismiss();

                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }else {
                    pic.setClickable(false);
                    update.setClickable(false);
                    pd.dismiss();
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setMessage("Accés reservé aux pros");
                    alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Fragment fragment = new fragment_main();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction ft = fragmentManager.beginTransaction();
                            ft.replace(R.id.screenArea,fragment);
                            ft.commit();
                            navigation.getMenu().getItem(0).setChecked(true);



                        }
                    });
                    alert.show();

                }

            }
        });
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (profileimageurl!=null)
               {
                   deletepicref = FirebaseStorage.getInstance().getReferenceFromUrl(profileimageurl);
                   deletepicref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void aVoid) {
                           pd.show();

                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
                       }
                   }).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           pd.dismiss();
                           showimageChooser();
                       }
                   });

               }
               else {
                   showimageChooser();
               }


            }
        });
        updateref = FirebaseDatabase.getInstance().getReference().child("pros").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verif(name.getText().toString().trim(), tel.getText().toString().trim())) {
                    Map<String, Object> hopperUpdates = new HashMap<>();
                    hopperUpdates.put("u", name.getText().toString().trim());
                    hopperUpdates.put("t", tel.getText().toString().trim());
                    hopperUpdates.put("b", bio.getText().toString().trim());
                    if (profileimageurl!=null) {
                        if (oldpicurl == null) {
                            updateref.child("pp").setValue(profileimageurl);
                            profileimageurl=null;

                        }else {
                            hopperUpdates.put("pp", profileimageurl);
                            profileimageurl=null;
                            deletepicref = FirebaseStorage.getInstance().getReferenceFromUrl(oldpicurl);
                            deletepicref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                    updateref.updateChildren(hopperUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getActivity(), "mis à jour", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    });
                }

            }


            private boolean verif(String trim, String trim1) {
                if (trim.length()==0){
                    name.setError("Veuillez saisir votre nom et prénom");
                    return false;
                }
                if (trim1.length()!=8){
                    tel.setError("Veuillez saisir votre numéro de teléphone composé de 8 chiffres");
                    return false;
                }



                return true;
            }
        });

    }
    private void readData(final FirebaseCallback firebaseCallback)
    {
        pd.show();
        firebaseDatabase=FirebaseDatabase.getInstance().
                getReference().child("client");
        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                {
                    bool=false;
                }
                else {
                    bool =true;
                }
                firebaseCallback.onCallback(bool);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private interface FirebaseCallback {
        void onCallback(Boolean b);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            uriprofileimage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uriprofileimage);

                pic.setImageBitmap(bitmap);
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
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    pd.show();
                    update.setEnabled(false);
                }
            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    pd.dismiss();
                    update.setEnabled(true);

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

