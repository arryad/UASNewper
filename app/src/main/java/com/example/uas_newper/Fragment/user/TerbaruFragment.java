package com.example.uas_newper.Fragment.user;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uas_newper.Adapter.ListNewsAdapterAdmin;
import com.example.uas_newper.Adapter.ListNewsAdapterUser;
import com.example.uas_newper.FirebaseUtils;
import com.example.uas_newper.Model.ItemModel;
import com.example.uas_newper.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TerbaruFragment extends Fragment {
    private String TAG = TerbaruFragment.class.getSimpleName();

    private ArrayList<ItemModel> listItem;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    ActionBar actionBar;



    public TerbaruFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_terbaru, container, false);

        initView(view);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        FirebaseUtils.getReference(FirebaseUtils.PATH_BERITA)
                .orderByChild("status")
                .equalTo("expose").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listItem = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ItemModel item = snapshot.getValue(ItemModel.class);
                    item.setKey(snapshot.getKey());
                    listItem.add(item);
                }
                adapter = new ListNewsAdapterUser(listItem, getActivity());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(getApplicationContext(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
                Log.d("MyListActivity", databaseError.getDetails() + " | " + databaseError.getMessage());
            }
        });
        return view;

    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.rv_newsItem);
    }

}
