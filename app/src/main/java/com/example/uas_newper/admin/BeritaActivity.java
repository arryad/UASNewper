package com.example.uas_newper.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.uas_newper.AddPostActivity;
import com.example.uas_newper.Fragment.admin.HomeFragment;
import com.example.uas_newper.Fragment.user.ListUserFragment;
import com.example.uas_newper.Fragment.user.SettingFragment;
import com.example.uas_newper.Fragment.user.TvFragment;
import com.example.uas_newper.MyPref;
import com.example.uas_newper.R;
import com.example.uas_newper.SplashscreenActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BeritaActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    MyPref myPref;
    TextView emailProfile;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita2);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Dashboard Admin");

        firebaseAuth = FirebaseAuth.getInstance();

        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(selectedListener);

        com.example.uas_newper.Fragment.admin.HomeFragment fragment = new HomeFragment();
        FragmentTransaction ftl= getSupportFragmentManager().beginTransaction();
        ftl.replace(R.id.content, fragment, "");
        ftl.commit();


    }


    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.item_home:
                    actionBar .setTitle("Home");
                    com.example.uas_newper.Fragment.admin.HomeFragment fragment = new com.example.uas_newper.Fragment.admin.HomeFragment();
                    FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content, fragment, "");
                    ft.commit();
                    return true;
                case R.id.item_profile:
                    actionBar.setTitle("Tv News");
                    TvFragment fragment1 = new TvFragment();
                    FragmentTransaction ftl = getSupportFragmentManager().beginTransaction();
                    ftl.replace(R.id.content, fragment1, "");
                    ftl.commit();
                    return true;
//                case R.id.item_listUser:
//                    actionBar.setTitle("List Users");
//                    ListUserFragment fragment3 = new ListUserFragment();
//                    FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
//                    ft3.replace(R.id.content, fragment3, "");
//                    ft3.commit();
//                    return true;
                case R.id.item_setting:
                    actionBar.setTitle("Setting");
                    SettingFragment fragment2 = new SettingFragment();
                    FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                    ft2.replace(R.id.content, fragment2, "");
                    ft2.commit();
                    return true;

            }
            return false;
        }
    };

    //check user status menggunakan auth
//    private void checkUserStatus(){
//        FirebaseUser user = firebaseAuth.getCurrentUser();
//        if(user != null){
//            emailProfile.setText(user.getEmail());
//        } else{
//            startActivity(new Intent(BeritaActivity.this, MainActivity.class));
//            finish();
//        }
//    }

    //mendisablekan fitur back yang ada pada menu bar
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    //pertama kali di jalankan
    @Override
    protected void onStart() {
//        checkUserStatus();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        super.onStart();
    }

    //untuk menampilkan menu ...
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //untuk logout pada menu bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_logout){
            firebaseAuth.signOut();
//            myPref = new MyPref(this);
//            checkUserStatus();
//            myPref.saveSPBoolean(MyPref.ISLOGIN, false);
            MyPref.getEditor().clear().commit();
            startActivity(new Intent(BeritaActivity.this, SplashscreenActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        } if (id == R.id.action_listuser){
            startActivity(new Intent(BeritaActivity.this, ListUserActivity.class));
            finish();
        } if (id == R.id.action_add_post){
            startActivity(new Intent(BeritaActivity.this, AddPostActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
