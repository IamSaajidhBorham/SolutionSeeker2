package com.example.saajidh.solutionseeker;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Saajidh on 7/1/2017.
 */

public class solutionseekerDB extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
    }
}
