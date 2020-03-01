package com.loukil.Contactini;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

class Profileadapter extends RecyclerView.Adapter<Profileadapter.ViewHolder> {
    private List<prodb> mprofilesdata;
    private Context mctx;

    public Profileadapter(List<prodb> profilesdata, FragmentActivity activity) {
        mprofilesdata = profilesdata;
        mctx=activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.profilesrow,
                viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
       final prodb model = mprofilesdata.get(i);
        viewHolder.namefield.setText(model.getNom());
        viewHolder.categyfield.setText(model.getCategory());
        viewHolder.Telfield.setText(model.getTel());
        viewHolder.adressfield.setText(model.getVille());
        viewHolder.avgprofilesrating.setRating((model.getAvgrating()));

        Glide.with(mctx).load(model.getLink()).placeholder(R.drawable.face).apply(RequestOptions.circleCropTransform()).into(viewHolder.profileimage);
        viewHolder.profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent displayprofile = new Intent(mctx, Profile.class);
                displayprofile.putExtra("profileId", model.getFavid());
                mctx.startActivity(displayprofile);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mprofilesdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView profileimage;
        public TextView namefield;
        public TextView adressfield;
        public TextView Telfield;
        public TextView categyfield;
        public RatingBar avgprofilesrating ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileimage = itemView.findViewById(R.id.profilesrowimage);
            namefield = itemView.findViewById(R.id.profilesrowname);
            adressfield = itemView.findViewById(R.id.profilesrowadresse);
            Telfield = itemView.findViewById(R.id.profilesrowtel);
            categyfield = itemView.findViewById(R.id.categoryprofilerow);
            avgprofilesrating = itemView.findViewById(R.id.ratingavgprofiles);
        }
    }
}
