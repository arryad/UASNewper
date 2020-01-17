package com.example.uas_newper.Fragment.user;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uas_newper.Adapter.NewsTabAdapter;
import com.example.uas_newper.R;
import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    // When requested, this adapter returns a DemoObjectFragment,
    // representing an object in the collection.
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private NewsTabAdapter newsTabAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        this.viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        this.newsTabAdapter = new NewsTabAdapter(getChildFragmentManager());
        this.newsTabAdapter.AddFragment(new SportFragment(), "Sport");
        this.newsTabAdapter.AddFragment(new ProfileFragment(), "Register");
        this.newsTabAdapter.AddFragment(new ProfileFragment(), "Register");
        this.newsTabAdapter.AddFragment(new ProfileFragment(), "Register");
        this.newsTabAdapter.AddFragment(new ProfileFragment(), "Register");
        this.newsTabAdapter.AddFragment(new ProfileFragment(), "Register");
        this.newsTabAdapter.AddFragment(new ProfileFragment(), "Register");

        viewPager.setAdapter(this.newsTabAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}

