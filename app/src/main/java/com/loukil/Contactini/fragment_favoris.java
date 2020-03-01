package com.loukil.Contactini;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

;
import java.util.List;


public class fragment_favoris extends Fragment {

    private RecyclerView favouriterecycler;
    private RecyclerView.LayoutManager mlayoutmanager;
    private RecyclerView.Adapter madapter;
    private List<prodb> mdataset ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentfragment_favoris,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        final appDatabase db = Room.databaseBuilder(getActivity(),appDatabase.class,"production").allowMainThreadQueries().build();
        mdataset = db.PRO().getAllUsers();
        favouriterecycler = view.findViewById(R.id.favouriterecyclerview);
        mlayoutmanager = new LinearLayoutManager(getActivity());
        favouriterecycler.setLayoutManager(mlayoutmanager);

        madapter=new Profileadapter(mdataset,getActivity());


        favouriterecycler.setAdapter(madapter);
    }


    }


