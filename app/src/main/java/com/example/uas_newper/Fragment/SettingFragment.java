package com.example.uas_newper.Fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.uas_newper.MyPref;
import com.example.uas_newper.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {
    private String TAG = SettingFragment.class.getSimpleName();

    private TextView txtName, txtLevel;

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        initView(view);

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_setting, container, false);

        txtName.setText(MyPref.getSharedPreferences().getString(MyPref.NAME, "NAME"));
        txtLevel.setText(MyPref.getSharedPreferences().getString(MyPref.LEVEL, "LEVEL"));


        return view;
    }

    private void initView(View view) {
        txtName = view.findViewById(R.id.txt_Name);
        txtLevel = view.findViewById(R.id.txt_Level);
    }
}
