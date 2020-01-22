package com.example.uas_newper.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.uas_newper.LoginActivity;
import com.example.uas_newper.MyPref;
import com.example.uas_newper.R;
import com.example.uas_newper.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddUserActivity extends AppCompatActivity {
    private String TAG = EditUserActivity.class.getSimpleName();
    EditText emailT, pwT, pwTC, mName;
    Spinner level;
    Button registerBtn;
    TextView haveAcc;
    ActionBar actionBar;
    ProgressDialog progressDialog;
    MyPref myPref;
    ImageView bt_back;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        actionBar = getSupportActionBar();
        actionBar.hide();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        emailT = findViewById(R.id.emailText);
        pwT = findViewById(R.id.pwText);
        pwTC = findViewById(R.id.pwConfirmText);
        registerBtn = findViewById(R.id.register_btn);
        haveAcc = findViewById(R.id.have_account);
        mName = findViewById(R.id.namaText);
        myPref = new MyPref(this);
        level = findViewById(R.id.combobox);
        bt_back = findViewById(R.id.bt_back);

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddUserActivity.this, ListUserActivity.class));
                finish();
            }
        });

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering..");

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailT.getText().toString().trim();
                String password = pwT.getText().toString().trim();
                String nameT = mName.getText().toString().trim();


                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailT.setError("Invalid Email");
                    emailT.setFocusable(true);
                } else if (password.length() < 6) {
                    pwT.setError("Invalid Password length less than 6 character");
                    pwT.setFocusable(true);
                } else if (nameT.isEmpty()){
                    mName.setError("Invalid Email");
                    mName.setFocusable(true);
                } else {
                    if (!pwT.getText().toString().trim().equals(pwTC.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Password doesn't match", Toast.LENGTH_SHORT).show();
                        System.out.println(pwT.getText());
                        System.out.println(pwTC.getText());
                    } else {
                        RegisterUser(email, password, nameT);
                    }
                }
            }
        });

    }

    private void RegisterUser(String email, String password, final String nameT) {

        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            progressDialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();
                            String email = user.getEmail();
                            String uid = user.getUid();
                            String lv = level.getSelectedItem().toString();

                            HashMap<Object, String> hashMap = new HashMap<>();
                            hashMap.put("email", email);
                            hashMap.put("name", nameT);
                            hashMap.put("level", lv);
                            hashMap.put("image", "");

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("Users");
                            reference.child(uid).setValue(hashMap);

                            Toast.makeText(AddUserActivity.this, "Registered" + user.getEmail(), Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Toast.makeText(AddUserActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(AddUserActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
