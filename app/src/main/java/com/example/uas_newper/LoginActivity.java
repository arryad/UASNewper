package com.example.uas_newper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uas_newper.Model.AccountModel;
import com.example.uas_newper.user.BeritaActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference database;
    private ArrayList<AccountModel> dataAkun;
    private String jenisUser, idUser, nameUser;
    private MyPref myPref;

    private static final int RC_SIGN_IN = 100;
    GoogleSignInClient mGoogleSignInClient;


    EditText emailT,pwT;
    TextView regis,recoverPw;
    Button logIn;
    ImageButton LoginG;
    ActionBar actionBar;
    private FirebaseAuth mAuth;

    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myPref = new MyPref(this);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Login");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        emailT = findViewById(R.id.emailText);
        pwT = findViewById(R.id.pwText);
        regis = findViewById(R.id.register_btn);
        logIn = findViewById(R.id.login_btn);
        recoverPw = findViewById(R.id.recoverPass);
        LoginG = findViewById(R.id.googleLogin);


        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();




        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailT.getText().toString();
                String password = pwT.getText().toString().trim();
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailT.setError("Invalid Email");
                    emailT.setFocusable(true);
                } else{
                    loginUser(email, password);
                }
            }
        });

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });

        recoverPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRecoverPasswordDialog();
            }
        });

        LoginG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        pd = new ProgressDialog(this);



    }

    private void showRecoverPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Recover Password");

        LinearLayout linearLayout = new LinearLayout(this);
        final EditText emailEt= new EditText(this);
        emailEt.setHint("Email");
        emailEt.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        emailEt.setMinEms(16);

        linearLayout.addView(emailEt);
        linearLayout.setPadding(10, 10, 10, 10);

        builder.setView(linearLayout);

        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email = emailEt.getText().toString().trim();
                beginRecovery(email);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    private void beginRecovery(String email) {
        pd.setMessage("Sending email...");
        pd.show();
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Email sent", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(LoginActivity.this, "Failed..", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(LoginActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loginUser(String email, String password) {
        pd.setMessage("Logging In..");
        pd.show();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("email").equalTo(emailT.getText().toString());
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        dataAkun = new ArrayList<>();
                                        dataAkun.clear();
                                        for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                                            AccountModel account = snapshot.getValue(AccountModel.class);
                                            dataAkun.add(account);
                                            pd.dismiss();
                                            // Sign in success, update UI with the signed-in user's information
                                            FirebaseUser user = mAuth.getCurrentUser();
                                        }

                                        jenisUser = dataAkun.get(0).getLevel();
                                        idUser = dataAkun.get(0).getId();
                                        nameUser = dataAkun.get(0).getName();

                                        Toast.makeText(LoginActivity.this, jenisUser, Toast.LENGTH_SHORT).show();
                                        if(jenisUser.equals("admin")){
                                            myPref.saveSPString(MyPref.LEVEL, jenisUser);
                                            myPref.saveSPString(MyPref.ID, idUser);
                                            myPref.saveSPString(MyPref.NAME, nameUser);
                                            myPref.saveSPBoolean(MyPref.ISLOGIN, true);
                                            startActivity(new Intent(LoginActivity.this, BeritaActivity.class)
                                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                            finish();
                                        } else if(jenisUser.equals("user")){
                                            myPref.saveSPString(MyPref.LEVEL, jenisUser);
                                            myPref.saveSPString(MyPref.ID, idUser);
                                            myPref.saveSPString(MyPref.NAME, nameUser);
                                            myPref.saveSPBoolean(MyPref.ISLOGIN, true);
                                            startActivity(new Intent(LoginActivity.this, BeritaActivity.class)
                                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                            finish();
                                        }
//                                        MyPref.getEditor().putBoolean(MyPref.ISLOGIN, true);
//                                        MyPref.getEditor().putString(MyPref.NAME, account.getName());
//                                        MyPref.getEditor().putString(MyPref.LEVEL, account.getLevel());
//                                        MyPref.getEditor().putString(MyPref.EMAIL, account.getEmail());
//                                        MyPref.getEditor().putString(MyPref.IMAGE, account.getImage());
//                                        MyPref.getEditor().commit();

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Log.d("error", databaseError.getDetails());
                                }
                            });

                        } else {
                            pd.dismiss();
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(LoginActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this,""+e.getMessage(), Toast.LENGTH_SHORT).show();
                // ...
            }
        }
    }


    //belumdipakai
    private void cekUser(){
        Query query = database.child("Users").orderByChild("email").equalTo(emailT.getText().toString());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataAkun = new ArrayList<AccountModel>();
                dataAkun.clear();
                for(DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()){
                    AccountModel account= noteDataSnapshot.getValue(AccountModel.class);
                    dataAkun.add(account);
                }
                jenisUser = dataAkun.get(0).getLevel();
                idUser = dataAkun.get(0).getId();

                Toast.makeText(LoginActivity.this, jenisUser, Toast.LENGTH_SHORT).show();
                if(jenisUser.equals("admin")){
                    //dashboardadmin
                }else{
                    //dashboarduser
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    //login menggunakan gooogle + auto terdaftar
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            String email = user.getEmail();
                            String uid = user.getUid();

                            HashMap<Object, String> hashMap = new HashMap<>();
                            hashMap.put("email", email);
                            hashMap.put("email", email);
                            hashMap.put("name", email);
                            hashMap.put("level", "user");
                            hashMap.put("image", "");

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("Users");
                            reference.child(uid).setValue(hashMap);

                            Toast.makeText(LoginActivity.this, ""+user.getEmail(), Toast.LENGTH_SHORT).show();
                            Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("email").equalTo(user.getEmail().toString());
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        dataAkun = new ArrayList<>();
                                        dataAkun.clear();
                                        for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                                            AccountModel account = snapshot.getValue(AccountModel.class);
                                            dataAkun.add(account);
                                            pd.dismiss();
                                            // Sign in success, update UI with the signed-in user's information
                                            FirebaseUser user = mAuth.getCurrentUser();
                                        }

                                        jenisUser = dataAkun.get(0).getLevel();
                                        idUser = dataAkun.get(0).getId();
                                        nameUser = dataAkun.get(0).getName();

                                        Toast.makeText(LoginActivity.this, jenisUser, Toast.LENGTH_SHORT).show();
                                        if(jenisUser.equals("admin")){

                                            startActivity(new Intent(LoginActivity.this, BeritaActivity.class)
                                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                            finish();
                                        } else if(jenisUser.equals("user")){
                                            myPref.saveSPString(MyPref.LEVEL, jenisUser);
                                            myPref.saveSPString(MyPref.ID, idUser);
                                            myPref.saveSPString(MyPref.NAME, nameUser);
                                            myPref.saveSPBoolean(MyPref.ISLOGIN, true);
                                            startActivity(new Intent(LoginActivity.this, BeritaActivity.class)
                                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                            finish();
                                        }
//                                        MyPref.getEditor().putBoolean(MyPref.ISLOGIN, true);
//                                        MyPref.getEditor().putString(MyPref.NAME, account.getName());
//                                        MyPref.getEditor().putString(MyPref.LEVEL, account.getLevel());
//                                        MyPref.getEditor().putString(MyPref.EMAIL, account.getEmail());
//                                        MyPref.getEditor().putString(MyPref.IMAGE, account.getImage());
//                                        MyPref.getEditor().commit();

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Log.d("error", databaseError.getDetails());
                                }
                            });

//                            startActivity(new Intent(LoginActivity.this, BeritaActivity.class));
//                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Login Failed...", Toast.LENGTH_SHORT);

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
