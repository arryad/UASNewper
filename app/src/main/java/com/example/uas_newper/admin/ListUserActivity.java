package com.example.uas_newper.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.uas_newper.Adapter.Account;
import com.example.uas_newper.Adapter.ListUserAdapter;
import com.example.uas_newper.FirebaseUtils;
import com.example.uas_newper.MyPref;
import com.example.uas_newper.R;
import com.example.uas_newper.SplashscreenActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListUserActivity extends AppCompatActivity implements ListUserAdapter.ItemListener {
    private ArrayList<Account> listItem;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private RecyclerView rvItem;
    private ImageView bt_back, bt_add;

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        actionBar = getSupportActionBar();
        actionBar.hide();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        bt_back = findViewById(R.id.bt_back);
        rvItem = findViewById(R.id.rv_item);
        bt_add = findViewById(R.id.action_tambah);

        layoutManager = new LinearLayoutManager(ListUserActivity.this);
        rvItem.setLayoutManager(layoutManager);

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListUserActivity.this, BeritaActivity.class));
                finish();
            }
        });


        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListUserActivity.this, AddUserActivity.class));
                finish();
            }
        });
        FirebaseUtils.getReference(FirebaseUtils.ITEM_PATH).orderByChild("level").equalTo("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listItem = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Account item = snapshot.getValue(Account.class);
                    item.setKey(snapshot.getKey());
                    listItem.add(item);
                }
                adapter = new ListUserAdapter(listItem, ListUserActivity.this, ListUserActivity.this);
                rvItem.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(getApplicationContext(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
                Log.d("MyListActivity", databaseError.getDetails() + " | " + databaseError.getMessage());
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_in_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //untuk logout pada menu bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_user) {
            startActivity(new Intent(ListUserActivity.this, AddUserActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void deleteItem(Account item) {
        FirebaseUtils.getReference(FirebaseUtils.ITEM_PATH).child(item.getKey()).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ListUserActivity.this, "Data berhasil di hapus.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
