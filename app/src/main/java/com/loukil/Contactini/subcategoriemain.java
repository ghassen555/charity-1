package com.loukil.Contactini;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class subcategoriemain extends AppCompatActivity {

     public ArrayList<String> datas;
    private RecyclerView subrecycler;
  //  public  ArrayList<String> datas;
    private RecyclerView.LayoutManager layoutmanager;
    private RecyclerView.Adapter madapter;
    private String subselectedcategory;
    private DatabaseReference catref;
    private ProgressBar progressBar ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategoriemain);

        datas=new ArrayList<>();
        if (getIntent().hasExtra("subcategoryselected")) {
            subselectedcategory = getIntent().getStringExtra("subcategoryselected");
        }

        //

        progressBar=findViewById(R.id.progresssubcat);
        subrecycler = findViewById(R.id.subcatrecyclerview);
        layoutmanager = new LinearLayoutManager(this);
        subrecycler.setHasFixedSize(true);
        subrecycler.setLayoutManager(layoutmanager);
        progressBar.setVisibility(View.VISIBLE);
       readData(new FirebaseCallBack() {
           @Override
           public void onCallBack(ArrayList<String> list) {
               madapter = new subcatadapter(datas,subcategoriemain.this);
               subrecycler.setAdapter(madapter);
               progressBar.setVisibility(View.GONE);
           }
       });


    }
    private void readData(final FirebaseCallBack firebaseCallBack)
    {
        catref = FirebaseDatabase.getInstance().getReference().child("CATEGORIS").child(subselectedcategory);
        catref.addValueEventListener(new ValueEventListener()  {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot ) {


                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    datas.add(child.getValue().toString());

                }
            firebaseCallBack.onCallBack(datas);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public interface FirebaseCallBack {
        void onCallBack( ArrayList<String> list);
    }


   /* @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<myarray> options = new FirebaseRecyclerOptions.Builder<myarray>().setQuery(catref,java.lang.String).build();
        FirebaseRecyclerAdapter<myarray, subcategoriemain.DisplaySubcategories> adapter = new FirebaseRecyclerAdapter<myarray, subcategoriemain.DisplaySubcategories>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final subcategoriemain.DisplaySubcategories holder, int position, @NonNull final myarray model) {
                holder.suggest.setText(model.getData());
               holder.suggest.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent displayingprofiles = new Intent(subcategoriemain.this, Profiles.class);
                       displayingprofiles.putExtra("categoryselected", model.getData());
                       startActivity(displayingprofiles);
                   }
               });
            }

            @NonNull
            @Override
            public subcategoriemain.DisplaySubcategories onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row, viewGroup, false);
                subcategoriemain.DisplaySubcategories viewHolder = new subcategoriemain.DisplaySubcategories(view);
                return viewHolder;
            }
        };
        subrecycler.setAdapter(adapter);
        adapter.startListening();
    }

    public static class DisplaySubcategories extends RecyclerView.ViewHolder {
        public Button suggest;
        public DisplaySubcategories(@NonNull View itemView) {
            super(itemView);
        }
    }
*/



}
