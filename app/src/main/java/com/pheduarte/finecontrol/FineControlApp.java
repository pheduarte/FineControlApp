package com.pheduarte.finecontrol;

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
