package com.loukil.Contactini;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class fragment_main extends Fragment  {
    private RecyclerView mrecyclerview;
    private category mdataset[] = {
            new category("Pauvresse",R.drawable.pquv),
            new category("Sante",R.drawable.sante),
            new category("education",R.drawable.educ),
            new category("Faim",R.drawable.faim)
    };




    private RecyclerView.LayoutManager mlayoutmanager;
    private RecyclerView.Adapter madapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment ,null);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mrecyclerview = view.findViewById(R.id.recyclerview);
        mlayoutmanager = new GridLayoutManager(getActivity(),2);
        mrecyclerview.setHasFixedSize(true);
        mrecyclerview.setLayoutManager(mlayoutmanager);
        madapter = new Mainadapter(mdataset, getActivity());
        mrecyclerview.setAdapter(madapter);
      //  signupbutton = view.findViewById(R.id.signupbutton);
       // signinbutton = view.findViewById(R.id.signinbutton);
        //signinbutton.setOnClickListener(this);
        //signupbutton.setOnClickListener(this);


    }




}
