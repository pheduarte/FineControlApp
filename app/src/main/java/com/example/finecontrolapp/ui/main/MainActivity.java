package com.example.finecontrolapp.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.finecontrolapp.BudgetFragment;
import com.example.finecontrolapp.HomeFragment;
import com.example.finecontrolapp.NewFragment;
import com.example.finecontrolapp.ProfileFragment;
import com.example.finecontrolapp.R;
import com.example.finecontrolapp.databinding.NavBarMenuBinding;
import com.example.finecontrolapp.ui.main.TransactionsFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NavBarMenuBinding binding = NavBarMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String user = intent.getStringExtra("user");

        if (savedInstanceState == null) {
            HomeFragment homeFragment = HomeFragment.newInstance(user);
            replaceFragment(homeFragment);
        }

        binding.btnHome.setOnClickListener(v -> {
            HomeFragment homeFragment = HomeFragment.newInstance(user);
            replaceFragment(homeFragment);
        });

        binding.btnTransactions.setOnClickListener(v -> replaceFragment(new TransactionsFragment()));

        binding.btnNew.setOnClickListener(v -> replaceFragment(new NewFragment()));

        binding.btnBudget.setOnClickListener(v -> replaceFragment(new BudgetFragment()));

        binding.btnProfile.setOnClickListener(v -> replaceFragment(new ProfileFragment()));

    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.containerMain, fragment);
        transaction.commit();
    }
}
