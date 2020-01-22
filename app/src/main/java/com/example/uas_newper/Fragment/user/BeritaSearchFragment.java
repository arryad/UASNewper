package com.example.uas_newper.Fragment.user;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uas_newper.Adapter.ListNewsAdapterUser;
import com.example.uas_newper.FirebaseUtils;
import com.example.uas_newper.Model.ItemModel;
import com.example.uas_newper.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BeritaSearchFragment extends Fragment {
    private ProgressBar loading;
    private EditText et_search;
    private ImageButton bt_search;
    private ImageView iv_start;
    private TextView tv_total;
    private LinearLayout ll_no_data;
    private RecyclerView rv_loker;
    private ListNewsAdapterUser adapter;
    private ArrayList<ItemModel> lokerList;
    private DatabaseReference databaseReference;
    private String userId;
    private int total = 0;

    public BeritaSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_berita_search, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference("Berita");

        loading = v.findViewById(R.id.loading);
        et_search = v.findViewById(R.id.et_search);
        bt_search = v.findViewById(R.id.bt_search);
        iv_start = v.findViewById(R.id.iv_start);
        tv_total = v.findViewById(R.id.tv_total);
        ll_no_data = v.findViewById(R.id.ll_no_data);

        rv_loker = v.findViewById(R.id.rv_newsItem);
        rv_loker.setHasFixedSize(true);
        rv_loker.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_loker.setItemAnimator(new DefaultItemAnimator());

        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_search.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Jangan dikosongin dong :(", Toast.LENGTH_LONG).show();
                } else {
                    loadData(et_search.getText().toString());
                }
            }
        });

        return v;
    }

    private void loadData(String keyword) {
        loading.setVisibility(View.VISIBLE);
        iv_start.setVisibility(View.GONE);
        ll_no_data.setVisibility(View.GONE);

        if(TextUtils.isEmpty(userId)){
            userId = databaseReference.push().getKey();
        }

        Query query = databaseReference.orderByChild("judul").startAt(keyword).endAt(keyword+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                loading.setVisibility(View.GONE);
                total = 0;
                lokerList = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ItemModel loker = postSnapshot.getValue(ItemModel.class);
                    lokerList.add(loker);
                    total++;
                }

                if (lokerList.isEmpty()) {
                    ll_no_data.setVisibility(View.VISIBLE);
                    tv_total.setText("Menampilkan " + total + " data");
                } else {
                    tv_total.setText("Menampilkan " + total + " data");
                    adapter = new ListNewsAdapterUser(lokerList, getContext());
                    rv_loker.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
