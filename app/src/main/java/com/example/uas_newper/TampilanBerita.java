package com.example.uas_newper;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.uas_newper.Model.ItemModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class TampilanBerita extends AppCompatActivity {
    private TextView eJudul, eDesc, ePenulis, ePublisher, eTanggal, eSk, eStatus;
    private Button eSave, eDelete;
    private ImageView eBack,ePic;
    private ProgressBar loading;
    private DatabaseReference databaseReference;
    private ActionBar actionBar;
    private MyPref myPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampilan_berita);

        actionBar = getSupportActionBar();
        actionBar.hide();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        initView();

        loadData();


    }

    private void loadData() {
        final ItemModel item = (ItemModel) getIntent().getSerializableExtra("data");

        if (item != null) {
            String pic = item.getPic();

            if (!item.getPic().equals("-")) {
                Glide.with(getApplicationContext()).load(Uri.parse(item.getPic())).into(ePic);
            }
            eTanggal.setText(item.getDeadline());
            eDesc.setText(item.getDeskripsi());
            eJudul.setText(item.getJudul());
            eSk.setText(item.getKategori());
            ePenulis.setText(item.getNama());
            eStatus.setText(item.getStatus());
            ePublisher.setText(item.getPublisher());

            Toast.makeText(this, ""+ePublisher.getText(), Toast.LENGTH_SHORT).show();


            if(eStatus.getText().toString().equals("unexpose")){
                Toast.makeText(this, ""+eStatus, Toast.LENGTH_SHORT).show();
                eSave.setEnabled(true);;
            } else {
                eSave.setEnabled(false);
            }

            eBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            eSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateItem(item);
                }
            });

            eDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


        }
    }

    private void updateItem(ItemModel item) {
        item.setSk("expose" + eSk.getText().toString());
        item.setStatus("expose");
        item.setPublisher(myPref.getSPName());

        FirebaseUtils.getReference(FirebaseUtils.PATH_BERITA).child(item.getKey()).setValue(item);
        Toast.makeText(TampilanBerita.this, "Edit Success", Toast.LENGTH_SHORT).show();
        onBackPressed();

        FirebaseUtils.getReference(FirebaseUtils.PATH_BERITA).child(item.getKey()).setValue(item).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(TampilanBerita.this, "Update Berhasil", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }

    private void initView() {
        eDesc = findViewById(R.id.et_deskripsi);
        eTanggal = findViewById(R.id.et_deadline);
        eJudul = findViewById(R.id.et_judul);
        eSave = findViewById(R.id.bt_pub);
        eDelete = findViewById(R.id.bt_delete);
        eBack = findViewById(R.id.bt_back);
        eStatus = findViewById(R.id.et_status);
        eSk = findViewById(R.id.et_sk);
        loading = findViewById(R.id.loading);
        ePic = findViewById(R.id.et_foto);
        ePenulis = findViewById(R.id.et_penulis);
        ePublisher = findViewById(R.id.et_publisher);

        databaseReference = FirebaseDatabase.getInstance().getReference("Berita");

//        storage = FirebaseStorage.getInstance();
//        storageReference = storage.getReference();
        myPref = new MyPref(getApplicationContext());
    }
}
