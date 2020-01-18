package com.example.uas_newper.Fragment.admin;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.uas_newper.MyPref;
import com.example.uas_newper.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {
    private String TAG = SettingFragment.class.getSimpleName();

    private TextView txtName, txtLevel;
    private MyPref myPref;
    private FragmentActivity myContext;

//    @Override
//    public void onAttach(Activity activity){
//        myContext = (FragmentActivity) activity;
//        super.onAttach(activity);
//    }

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        initView(view);

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_setting, container, false);

        myPref = new MyPref(getContext());
        txtName.setText(myPref.getSPName());
        txtLevel.setText(myPref.getSPLevel());

//        txtName.setText(MyPref.getSharedPreferences().getString(MyPref.NAME, "NAME"));
//        txtLevel.setText(MyPref.getSharedPreferences().getString(MyPref.LEVEL, "LEVEL"));
        return view;
    }

    private void initView(View view) {
        txtName = view.findViewById(R.id.txt_Name);
        txtLevel = view.findViewById(R.id.txt_Level);
    }
}
