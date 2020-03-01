package com.loukil.Contactini;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

class subcatadapter extends RecyclerView.Adapter<subcatadapter.ViewHolder> {
     private ArrayList<String> dataset;
     private android.content.Context Context ;
     public subcatadapter(ArrayList<String> mdataset , Context mcontext) {
         this.dataset = mdataset;
         this.Context = mcontext;
     }

     @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
         View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row, viewGroup, false);
         return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.msuggest.setText(dataset.get(i));
        viewHolder.msuggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent displayingprofiles = new Intent(Context, Profiles.class);
                displayingprofiles.putExtra("categoryselected",dataset.get(i));
                Context.startActivity(displayingprofiles);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public Button msuggest;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            msuggest = itemView.findViewById(R.id.suggest);
        }
    }
}
