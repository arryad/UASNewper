package com.example.uas_newper.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.uas_newper.Adapter.ListNewsAdapterAdmin;
import com.example.uas_newper.FirebaseUtils;
import com.example.uas_newper.Model.ItemModel;
import com.example.uas_newper.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListNewsActivity extends AppCompatActivity implements ListNewsAdapterAdmin.ItemListener{
    private ArrayList<ItemModel> listItem;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private RecyclerView rv_newItem;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_news);

        actionBar = getSupportActionBar();
        actionBar.setTitle("News");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        rv_newItem = findViewById(R.id.rv_newsItem);
        layoutManager = new LinearLayoutManager(ListNewsActivity.this);
        rv_newItem.setLayoutManager(layoutManager);

        FirebaseUtils.getReference(FirebaseUtils.ITEM_PATH).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listItem = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ItemModel item = snapshot.getValue(ItemModel.class);
                    item.setKey(snapshot.getKey());
                    listItem.add(item);
                }
                adapter = new ListNewsAdapterAdmin(listItem, getApplicationContext(), ListNewsActivity.this);
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
    public void deleteItem(ItemModel item) {
        FirebaseUtils.getReference(FirebaseUtils.PATH_BERITA).child(item.getKey()).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Data berhasil di hapus.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
