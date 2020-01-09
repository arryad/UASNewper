package com.example.uas_newper;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPref {
    private Context context;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public static final String NEWPER = "NEWPER";
    public static final String ISLOGIN = "ISLOGIN";
    public static final String NAME = "NAME";
    public static final String EMAIL = "EMAIL";
    public static final String IMAGE = "IMAGE";
    public static final String ID = "ID";
    public static final String LEVEL = "LEVEL";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public MyPref(Context context){
        sp = context.getSharedPreferences(NEWPER, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public static SharedPreferences getSharedPreferences(){
        return sharedPreferences;
    }

    public Boolean getSPISLOGIN(){
        return sp.getBoolean(ISLOGIN, false);
    }


    public static SharedPreferences.Editor getEditor(){
        return editor;
    }

    public String getSPLevel(){
        return sp.getString(LEVEL,"");
    }

    public String getSPId(){
        return sp.getString(ID, "");
    }

    public String getSPEmail(){
        return sp.getString(EMAIL, "");
    }







}
