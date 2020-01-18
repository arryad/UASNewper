package com.example.uas_newper.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.uas_newper.Adapter.Account;
import com.example.uas_newper.Adapter.ListUserAdapter;
import com.example.uas_newper.MyPref;
import com.example.uas_newper.R;
import com.example.uas_newper.SplashscreenActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListUserActivity extends AppCompatActivity {
    private ArrayList<Account> listItem;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private RecyclerView rvItem;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        actionBar = getSupportActionBar();
        actionBar.setTitle("List Account");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        rvItem = findViewById(R.id.rv_item);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        rvItem.setLayoutManager(layoutManager);

        FirebaseUtils.getReference(FirebaseUtils.ITEM_PATH).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listItem = new ArrayList<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Account item = snapshot.getValue(Account.class);
                    item.setKey(snapshot.getKey());
                    listItem.add(item);
                }
                adapter = new ListUserAdapter(listItem, ListUserActivity.this);
                rvItem.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(getApplicationContext(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
                Log.d("MyListActivity", databaseError.getDetails()+ " | " +databaseError.getMessage());
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_in_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //untuk logout pada menu bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_user){
            startActivity(new Intent(ListUserActivity.this, AddUserActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
