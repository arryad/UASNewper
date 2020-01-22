package com.example.uas_newper.Fragment.user;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uas_newper.Adapter.Account;
import com.example.uas_newper.Adapter.ListUserAdapter;
import com.example.uas_newper.FirebaseUtils;
import com.example.uas_newper.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListUserFragment extends Fragment {
    private String TAG = ListUserFragment.class.getSimpleName();

    private ArrayList<Account> listItem;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private RecyclerView rvItem;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_user, container, false);

        initView(view);

        layoutManager = new LinearLayoutManager(getActivity());
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
                adapter = new ListUserAdapter(listItem, getActivity());
                rvItem.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getDetails()+ " | " +databaseError.getMessage());
            }
        });

        return view;
    }

    private void initView(View view) {
        rvItem = view.findViewById(R.id.rv_item);
    }


}
