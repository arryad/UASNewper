package com.example.uas_newper.Fragment.admin;


import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uas_newper.Adapter.Account;
import com.example.uas_newper.Adapter.ListNewsAdapterAdmin;
import com.example.uas_newper.Adapter.ListNewsAdapterUser;
import com.example.uas_newper.Adapter.ListUserAdapter;
import com.example.uas_newper.FirebaseUtils;
import com.example.uas_newper.Model.ItemModel;
import com.example.uas_newper.R;
import com.example.uas_newper.TampilanBerita;
import com.example.uas_newper.admin.ListUserActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BelumPublikasiFragment extends Fragment implements ListNewsAdapterAdmin.ItemListener {
    private String TAG = BelumPublikasiFragment.class.getSimpleName();

    private ArrayList<ItemModel> listItem;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    ActionBar actionBar;


    public BelumPublikasiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_publikasi, container, false);

        initView(view);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        FirebaseUtils.getReference(FirebaseUtils.PATH_BERITA).orderByChild("status").equalTo("unexpose").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listItem = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ItemModel item = snapshot.getValue(ItemModel.class);
                    item.setKey(snapshot.getKey());
                    listItem.add(item);
                }
                adapter = new ListNewsAdapterAdmin(listItem, getActivity(), BelumPublikasiFragment.this);
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

    @Override
    public void deleteItem(final ItemModel item) {
//        String imgUrl = FirebaseUtils.getReference(FirebaseUtils.PATH_BERITA).orderByChild("pic").toString();
//        System.out.println(imgUrl);
//        StorageReference photoRef = FirebaseStorage.getInstance().getReferenceFromUrl(imgUrl);
//        photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                Toast.makeText(getActivity(), "Foto Berhasil Di hapus", Toast.LENGTH_SHORT).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getActivity(), "Foto Gagal Di hapus", Toast.LENGTH_SHORT).show();
//            }
//        });

        StorageReference photoRef = FirebaseStorage.getInstance().getReferenceFromUrl(item.getPic());
        photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                FirebaseUtils.getReference(FirebaseUtils.PATH_BERITA).child(item.getKey()).removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getActivity(), "Data Berhasil di hapus", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
//        FirebaseUtils.getReference(FirebaseUtils.PATH_BERITA).child(item.getKey()).removeValue()
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(getActivity(), "Data berhasil di hapus.", Toast.LENGTH_SHORT).show();


//                        StorageReference photoRef = FirebaseStorage.getInstance().getReferenceFromUrl(item.getPic());
//                        Toast.makeText(getActivity(), ""+photoRef+item.getPic(), Toast.LENGTH_LONG).show();
//                        photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Toast.makeText(getActivity(), "Data berhasil di hapus.", Toast.LENGTH_SHORT).show();
//                            }
//                        });

    }
}
