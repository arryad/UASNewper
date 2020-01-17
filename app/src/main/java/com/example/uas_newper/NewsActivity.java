package com.example.uas_newper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.uas_newper.Fragment.user.HomeFragment;
import com.example.uas_newper.Fragment.user.PSInFragment;
import com.example.uas_newper.Fragment.user.ProfileFragment;
import com.example.uas_newper.Fragment.user.SettingFragment;
import com.example.uas_newper.Fragment.user.TvFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseUser;

public class NewsActivity extends AppCompatActivity {

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        actionBar = getSupportActionBar();
        actionBar.setTitle("NewsPaper");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(selectedListener);

        HomeFragment fragment = new HomeFragment();
        FragmentTransaction ftl= getSupportFragmentManager().beginTransaction();
        ftl.replace(R.id.content, fragment, "");
        ftl.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.item_home:
                    actionBar .setTitle("News Paper");
                    HomeFragment fragment = new HomeFragment();
                    FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content, fragment, "");
                    ft.commit();
                    return true;
                case R.id.item_profile:
                    actionBar.setTitle("TV News");
                    TvFragment fragment1 = new TvFragment();
                    FragmentTransaction ftl = getSupportFragmentManager().beginTransaction();
                    ftl.replace(R.id.content, fragment1, "");
                    ftl.commit();
                    return true;
                case R.id.item_setting:
                    actionBar.setTitle("Setting");
                    PSInFragment fragment2 = new PSInFragment();
                    FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                    ft2.replace(R.id.content, fragment2, "");
                    ft2.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onStart() {
//        checkUserStatus();
        super.onStart();
    }
}
