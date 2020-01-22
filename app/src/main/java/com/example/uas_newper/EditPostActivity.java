package com.example.uas_newper;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uas_newper.Model.ItemModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditPostActivity extends AppCompatActivity {
    private String TAg = EditPostActivity.class.getSimpleName();

    private final int PICK_IMAGE_REQUEST = 22;

    private TextView txtName, txtLevel;
    private MyPref myPref;
    private FragmentActivity myContext;

    private ImageView bt_back, iv_pic;
    private ImageButton ib_upload;
    private EditText et_deskripsi, et_deadline, et_judul;
    private Spinner et_cb;
    private Button bt_simpan;
    private ProgressBar loading;
    private String email, userId, pic;
    private SharedPreferences pref;
    private DatabaseReference databaseReference;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Uri filePath;
    private Boolean upload_pic;
    private ActionBar actionBar;

    private Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        initView();

        final ItemModel item = (ItemModel) getIntent().getSerializableExtra("data");

        if (item != null) {
            et_deadline.setText(item.getDeadline());
            et_deskripsi.setText(item.getDeskripsi());
            et_judul.setText(item.getJudul());
            bt_simpan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateItem(item);
                }
            });
        }

        et_deadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditPostActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et_deadline.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateItem(ItemModel item){
        item.setDeadline(et_deadline.getText().toString().trim());
        item.setDeskripsi(et_deskripsi.getText().toString().trim());
        item.setJudul(et_judul.getText().toString().trim());

        FirebaseUtils.getReference(FirebaseUtils.PATH_BERITA).child(item.getKey()).setValue(item);
        Toast.makeText(EditPostActivity.this, "Edit Success", Toast.LENGTH_SHORT).show();
        onBackPressed();

        FirebaseUtils.getReference(FirebaseUtils.PATH_BERITA).child(item.getKey()).setValue(item).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(EditPostActivity.this, "Update Berhasil", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }

    public void initView(){
        et_deskripsi = findViewById(R.id.et_deskripsi);
        et_deadline = findViewById(R.id.et_deadline);
        et_judul = findViewById(R.id.et_judul);
        et_cb = findViewById(R.id.et_cb);
        upload_pic = false;
        iv_pic = findViewById(R.id.iv_pic);
        ib_upload = findViewById(R.id.ib_upload);
        bt_simpan = findViewById(R.id.bt_simpan);
        bt_back = findViewById(R.id.bt_back);
        loading = findViewById(R.id.loading);

        databaseReference = FirebaseDatabase.getInstance().getReference("Berita");

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        myPref = new MyPref(getApplicationContext());
    }
}
