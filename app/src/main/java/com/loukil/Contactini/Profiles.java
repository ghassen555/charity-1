package com.loukil.Contactini;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class Profiles extends AppCompatActivity {

    private RecyclerView mrecyclerview;
    //  private ArrayList<pros> profilesdata;
    private RecyclerView.LayoutManager mlayoutmanager;
    private Query proref;
    private String selectedcategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles);
        if (getIntent().hasExtra("assselected")) {
            selectedcategory = getIntent().getStringExtra("assselected");
        }




        // profilesdata = new ArrayList<>();


        mrecyclerview = findViewById(R.id.RecyclerviewProfiles);
        mlayoutmanager = new LinearLayoutManager(this);
        mrecyclerview.setHasFixedSize(true);
        mrecyclerview.setLayoutManager(mlayoutmanager);


    }

    @Override
    protected void onStart() {
        super.onStart();
        proref = FirebaseDatabase.getInstance().getReference().child("association").orderByChild("category").equalTo(selectedcategory);
        FirebaseRecyclerOptions<associa> options = new FirebaseRecyclerOptions.Builder<associa>().setQuery(proref, associa.class).build();
        FirebaseRecyclerAdapter<associa, DisplayProfilesViewHolder> adapter = new FirebaseRecyclerAdapter<associa, DisplayProfilesViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final DisplayProfilesViewHolder holder, int position, @NonNull final associa model) {
                holder.namefield.setText(model.getNom());
                holder.categyfield.setText(model.getCategory());
                holder.Telfield.setText(model.getTel());
                holder.adressfield.setText(model.getAdres());
                holder.avgprofilesrating.setRating((model.getRating()));

                Glide.with(Profiles.this).load(model.getLogopic()).placeholder(R.drawable.face).apply(RequestOptions.circleCropTransform()).into(holder.profileimage);
                holder.profilesrowcard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent displayprofile = new Intent(Profiles.this, Profile.class);
                        displayprofile.putExtra("pi", model.getId());
                        startActivity(displayprofile);
                    }
                });
            }

            @NonNull
            @Override
            public DisplayProfilesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.profilesrow, viewGroup, false);
                DisplayProfilesViewHolder viewHolder = new DisplayProfilesViewHolder(view);
                return viewHolder;
            }
        };
        mrecyclerview.setAdapter(adapter);
        adapter.startListening();



                /*    if (position==0)
                {

                  proref = FirebaseDatabase.getInstance().getReference().child("pros").orderByChild("cat").equalTo(selectedcategory);
                    FirebaseRecyclerOptions<pros> options = new FirebaseRecyclerOptions.Builder<pros>().setQuery(proref, pros.class).build();
        FirebaseRecyclerAdapter<pros, DisplayProfilesViewHolder> adapter = new FirebaseRecyclerAdapter<pros, DisplayProfilesViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final DisplayProfilesViewHolder holder, int position, @NonNull final pros model) {
                holder.namefield.setText(model.getUsername());
                holder.categyfield.setText(model.getCat());
              holder.Telfield.setText(model.getTel());
              String ville = model.getCatreg();
              holder.adressfield.setText(ville.substring(ville.indexOf("_")+1));
            holder.avgprofilesrating.setRating((model.getAvgrating()));

           Glide.with(Profiles.this).load(model.getProfile_pic()).placeholder(R.drawable.face).apply(RequestOptions.circleCropTransform()).into(holder.profileimage);
           holder.profileimage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent displayprofile = new Intent(Profiles.this, Profile.class);
                        displayprofile.putExtra("profileId", model.getProid());
                        startActivity(displayprofile);
                    }
                });
            }

            @NonNull
            @Override
            public DisplayProfilesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.profilesrow, viewGroup, false);
                DisplayProfilesViewHolder viewHolder = new DisplayProfilesViewHolder(view);
                return viewHolder;
            }
        };
        mrecyclerview.setAdapter(adapter);
        adapter.startListening();

                }
                else
                if (position==1)
                {
                    String filter = selectedcategory+"_Tunis";
                    proref = FirebaseDatabase.getInstance().getReference().child("pros").orderByChild("catreg").equalTo(filter);
                    FirebaseRecyclerOptions<pros> options = new FirebaseRecyclerOptions.Builder<pros>().setQuery(proref, pros.class).build();
                    FirebaseRecyclerAdapter<pros, DisplayProfilesViewHolder> adapter = new FirebaseRecyclerAdapter<pros, DisplayProfilesViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull final DisplayProfilesViewHolder holder, int position, @NonNull final pros model) {
                            holder.namefield.setText(model.getUsername());
                            holder.categyfield.setText(model.getCat());
                            holder.Telfield.setText(model.getTel());
                            String ville = model.getCatreg();
                            holder.adressfield.setText(ville.substring(ville.indexOf("_")+1));
                            holder.avgprofilesrating.setRating((model.getAvgrating()));

                            Glide.with(Profiles.this).load(model.getProfile_pic()).placeholder(R.drawable.face).apply(RequestOptions.circleCropTransform()).into(holder.profileimage);
                            holder.profileimage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent displayprofile = new Intent(Profiles.this, Profile.class);
                                    displayprofile.putExtra("profileId", model.getProid());
                                    startActivity(displayprofile);
                                }
                            });
                        }

                        @NonNull
                        @Override
                        public DisplayProfilesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.profilesrow, viewGroup, false);
                            DisplayProfilesViewHolder viewHolder = new DisplayProfilesViewHolder(view);
                            return viewHolder;
                        }
                    };
                    mrecyclerview.setAdapter(adapter);
                    adapter.startListening();

                }
                else if (position==2)
                {
                    String filter = selectedcategory+"_Sfax";
                    proref = FirebaseDatabase.getInstance().getReference().child("pros").orderByChild("catreg").equalTo(filter);
                    FirebaseRecyclerOptions<pros> options = new FirebaseRecyclerOptions.Builder<pros>().setQuery(proref, pros.class).build();
                    FirebaseRecyclerAdapter<pros, DisplayProfilesViewHolder> adapter = new FirebaseRecyclerAdapter<pros, DisplayProfilesViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull final DisplayProfilesViewHolder holder, int position, @NonNull final pros model) {
                            holder.namefield.setText(model.getUsername());
                            holder.categyfield.setText(model.getCat());
                            holder.Telfield.setText(model.getTel());
                            String ville = model.getCatreg();
                            holder.adressfield.setText(ville.substring(ville.indexOf("_")+1));
                            holder.avgprofilesrating.setRating((model.getAvgrating()));

                            Glide.with(Profiles.this).load(model.getProfile_pic()).placeholder(R.drawable.face).apply(RequestOptions.circleCropTransform()).into(holder.profileimage);
                            holder.profileimage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent displayprofile = new Intent(Profiles.this, Profile.class);
                                    displayprofile.putExtra("profileId", model.getProid());
                                    startActivity(displayprofile);
                                }
                            });
                        }

                        @NonNull
                        @Override
                        public DisplayProfilesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.profilesrow, viewGroup, false);
                            DisplayProfilesViewHolder viewHolder = new DisplayProfilesViewHolder(view);
                            return viewHolder;
                        }
                    };
                    mrecyclerview.setAdapter(adapter);
                    adapter.startListening();

                }
                else if (position==3)
                {
                    String filter = selectedcategory+"_Sousse";
                    proref = FirebaseDatabase.getInstance().getReference().child("pros").orderByChild("catreg").equalTo(filter);
                    FirebaseRecyclerOptions<pros> options = new FirebaseRecyclerOptions.Builder<pros>().setQuery(proref, pros.class).build();
                    FirebaseRecyclerAdapter<pros, DisplayProfilesViewHolder> adapter = new FirebaseRecyclerAdapter<pros, DisplayProfilesViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull final DisplayProfilesViewHolder holder, int position, @NonNull final pros model) {
                            holder.namefield.setText(model.getUsername());
                            holder.categyfield.setText(model.getCat());
                            holder.Telfield.setText(model.getTel());
                            String ville = model.getCatreg();
                            holder.adressfield.setText(ville.substring(ville.indexOf("_")+1));
                            holder.avgprofilesrating.setRating((model.getAvgrating()));
                            Glide.with(Profiles.this).load(model.getProfile_pic()).placeholder(R.drawable.face).apply(RequestOptions.circleCropTransform()).into(holder.profileimage);
                            holder.profileimage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent displayprofile = new Intent(Profiles.this, Profile.class);
                                    displayprofile.putExtra("profileId", model.getProid());
                                    startActivity(displayprofile);
                                }
                            });
                        }

                        @NonNull
                        @Override
                        public DisplayProfilesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.profilesrow, viewGroup, false);
                            DisplayProfilesViewHolder viewHolder = new DisplayProfilesViewHolder(view);
                            return viewHolder;
                        }
                    };
                    mrecyclerview.setAdapter(adapter);
                    adapter.startListening();

                }
                */
    }


       /* deligspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (deligspin.isEnabled())
                {
                    String filter = selectedcategory+"_"+deligspin.getSelectedItem().toString();
                    proref = FirebaseDatabase.getInstance().getReference().child("pros").orderByChild("catreg").equalTo(filter);
                    FirebaseRecyclerOptions<pros> options = new FirebaseRecyclerOptions.Builder<pros>().setQuery(proref, pros.class).build();
                    FirebaseRecyclerAdapter<pros, DisplayProfilesViewHolder> adapter = new FirebaseRecyclerAdapter<pros, DisplayProfilesViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull final DisplayProfilesViewHolder holder, int position, @NonNull final pros model) {
                            holder.namefield.setText(model.getU());
                            holder.categyfield.setText(model.getC());
                            holder.Telfield.setText(model.getT());
                            String ville = model.getCr();
                            holder.adressfield.setText(ville.substring(ville.indexOf("_")+1));
                            holder.avgprofilesrating.setRating((model.getA()));

                            Glide.with(Profiles.this).load(model.getPp()).placeholder(R.drawable.face).apply(RequestOptions.circleCropTransform()).into(holder.profileimage);
                            holder.profilesrowcard.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent displayprofile = new Intent(Profiles.this, Profile.class);
                                    displayprofile.putExtra("profileId", model.getPi());
                                    startActivity(displayprofile);
                                }
                            });
                        }

                        @NonNull
                        @Override
                        public DisplayProfilesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.profilesrow, viewGroup, false);
                            DisplayProfilesViewHolder viewHolder = new DisplayProfilesViewHolder(view);
                            return viewHolder;
                        }
                    };
                    mrecyclerview.setAdapter(adapter);
                    adapter.startListening();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
                */
       /* FirebaseRecyclerOptions<pros> options = new FirebaseRecyclerOptions.Builder<pros>().setQuery(proref, pros.class).build();
        FirebaseRecyclerAdapter<pros, DisplayProfilesViewHolder> adapter = new FirebaseRecyclerAdapter<pros, DisplayProfilesViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final DisplayProfilesViewHolder holder, int position, @NonNull final pros model) {
                holder.namefield.setText(model.getUsername());
               // holder.categyfield.setText(model.getCategory());
              holder.Telfield.setText(model.getTel());
            // holder.adressfield.setText(model.getRegion());
            holder.avgprofilesrating.setRating((model.getAvgrating()));

           Glide.with(Profiles.this).load(model.getProfile_pic()).placeholder(R.drawable.face).apply(RequestOptions.circleCropTransform()).into(holder.profileimage);
           holder.profileimage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent displayprofile = new Intent(Profiles.this, Profile.class);
                        displayprofile.putExtra("profileId", model.getProid());
                        startActivity(displayprofile);
                    }
                });
            }

            @NonNull
            @Override
            public DisplayProfilesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.profilesrow, viewGroup, false);
                DisplayProfilesViewHolder viewHolder = new DisplayProfilesViewHolder(view);
                return viewHolder;
            }
        };
        mrecyclerview.setAdapter(adapter);
        adapter.startListening();
        */


    public static class DisplayProfilesViewHolder extends RecyclerView.ViewHolder {
        public ImageView profileimage;
        public TextView namefield;
        public TextView adressfield;
        public TextView Telfield;
        public TextView categyfield;
        public RatingBar avgprofilesrating;
        public CardView profilesrowcard;

        public DisplayProfilesViewHolder(@NonNull View itemView) {
            super(itemView);
            profileimage = itemView.findViewById(R.id.profilesrowimage);
            namefield = itemView.findViewById(R.id.profilesrowname);
            adressfield = itemView.findViewById(R.id.profilesrowadresse);
            Telfield = itemView.findViewById(R.id.profilesrowtel);
            categyfield = itemView.findViewById(R.id.categoryprofilerow);
            avgprofilesrating = itemView.findViewById(R.id.ratingavgprofiles);
            profilesrowcard = itemView.findViewById(R.id.cardviewprofilesrow);
        }
    }
}

