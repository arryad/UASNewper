package com.example.uas_newper;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtils {
    public static final String MYNAME_PATH = "myname";
    public static final String ITEM_PATH = "Users";
    public static final String PATH_BERITA = "Berita";

    private static final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    public static DatabaseReference getReference(String path){
        return firebaseDatabase.getReference(path);
    }
}
