package com.example.uas_newper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.uas_newper.Adapter.ListNewsAdapterUser;
import com.example.uas_newper.Model.ItemModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListBeritaActivity extends AppCompatActivity {
    private ArrayList<ItemModel> listItem;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private RecyclerView rv_newItem;
    private MyPref myPref;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_berita_own);

        actionBar = getSupportActionBar();
        actionBar.setTitle("List Berita Anda");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        rv_newItem = findViewById(R.id.rv_newsItem);
        layoutManager = new LinearLayoutManager(ListBeritaActivity.this);
        rv_newItem.setLayoutManager(layoutManager);

        myPref = new MyPref(this);

        FirebaseUtils.getReference(FirebaseUtils.PATH_BERITA).orderByChild("nama").equalTo(myPref.getSPName().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listItem = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ItemModel item = snapshot.getValue(ItemModel.class);
                    item.setKey(snapshot.getKey());
                    listItem.add(item);
                }
                adapter = new ListNewsAdapterUser(listItem, getApplicationContext());
                rv_newItem.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(getApplicationContext(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
                Log.d("", databaseError.getDetails() + " | " + databaseError.getMessage());
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
