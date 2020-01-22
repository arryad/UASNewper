package com.example.uas_newper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uas_newper.user.BeritaActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText emailT, pwT, pwTC, mName;
    Button registerBtn;
    TextView haveAcc;
    ActionBar actionBar;
    ProgressDialog progressDialog;
    MyPref myPref;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
                } else if(nameT.isEmpty()){
                    mName.setError("Invalid Name");
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

        haveAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
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

                            HashMap<Object, String> hashMap = new HashMap<>();
                            hashMap.put("email", email);
                            hashMap.put("name", nameT);
                            hashMap.put("level", "user");
                            hashMap.put("image", "");

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("Users");
                            reference.child(uid).setValue(hashMap);

                            Toast.makeText(RegisterActivity.this, "Registered" + user.getEmail(), Toast.LENGTH_SHORT).show();
                            myPref.saveSPString(MyPref.LEVEL, "user");
                            myPref.saveSPString(MyPref.ID, uid);
                            myPref.saveSPString(MyPref.NAME, nameT);
                            myPref.saveSPBoolean(MyPref.ISLOGIN, true);
                            startActivity(new Intent(RegisterActivity.this, BeritaActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
