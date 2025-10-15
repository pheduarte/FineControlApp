package com.example.finecontrolapp.ui.main.ui.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.finecontrolapp.databinding.NavBarMenuBinding;

public class MainActivity extends AppCompatActivity {

    private NavBarMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = NavBarMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }


}