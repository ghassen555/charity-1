package com.loukil.Contactini;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

class Mainadapter extends RecyclerView.Adapter<Mainadapter.ViewHolder> {
    private category[] dataset;
    private Context Context ;

    public Mainadapter(category[] mdataset , Context mcontext) {
        this.dataset = mdataset;
        this.Context = mcontext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.catrow, viewGroup, false);
        return new ViewHolder(v);

    }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
            viewHolder.msuggest.setText(dataset[i].getNomcat());
        Glide.with(Context).load(dataset[i].getCatimg()).into(viewHolder.catimg);

        viewHolder.catimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent displaysubs = new Intent(Context, Profiles.class);
                  displaysubs.putExtra("assselected", dataset[i].getNomcat());
                  Context.startActivity(displaysubs);

            }
        });
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView msuggest;
        public ImageView catimg ;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            msuggest = itemView.findViewById(R.id.catsuggest);
            catimg=itemView.findViewById(R.id.catimg);
        }


    }
}