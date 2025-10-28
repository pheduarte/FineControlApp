package com.example.finecontrolapp;

import android.app.Application;
import com.google.firebase.FirebaseApp;

public class FineControlApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize Firebase
        FirebaseApp.initializeApp(this);
    }
}
