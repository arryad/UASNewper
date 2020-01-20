package com.example.uas_newper.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.uas_newper.Adapter.Account;
import com.example.uas_newper.MyPref;
import com.example.uas_newper.R;
import com.example.uas_newper.RegisterActivity;
import com.example.uas_newper.SplashscreenActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class EditUserActivity extends AppCompatActivity {
    private String TAG = EditUserActivity.class.getSimpleName();

    private EditText eName, eEmail, eLevel;
    private Button eButton;
    ActionBar actionBar;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    MyPref myPref;

    private String name, level, email, key;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Edit User");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        initView();

        final Account item = (Account) getIntent().getSerializableExtra("data");

        if (item != null) {
            eEmail.setText(item.getEmail());
            eLevel.setText(item.getLevel());
            eName.setText(item.getName());
            eButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateItem(item);
                }
            });
        }
    }

    private void updateItem(Account item) {
        item.setName(eEmail.getText().toString().trim());
        item.setName(eLevel.getText().toString().trim());
        item.setName(eName.getText().toString().trim());


        FirebaseUtils.getReference(FirebaseUtils.ITEM_PATH).child(item.getKey()).setValue(item);
        Toast.makeText(EditUserActivity.this, "Edit Success", Toast.LENGTH_SHORT).show();
        onBackPressed();

        FirebaseUtils.getReference(FirebaseUtils.ITEM_PATH).child(item.getKey()).setValue(item).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(EditUserActivity.this, "Update Berhasil", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });

    }

    private void initView() {
        eName = findViewById(R.id.editName);
        eEmail = findViewById(R.id.editEmail);
        eLevel = findViewById(R.id.editLevel);
        eButton = findViewById(R.id.editBtn);
    }

    private Boolean isValid(EditText editText, String data) {
        if (!TextUtils.isEmpty(data) && !data.equals("")) {
            return true;
        } else {
            editText.setError("This field cannot be empty");
            return false;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
