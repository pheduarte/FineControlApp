package com.example.finecontrolapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.finecontrolapp.ui.main.LoadingScreenFragment;

public class LoadingScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, LoadingScreenFragment.newInstance())
                    .commitNow();
        }
    }
}
