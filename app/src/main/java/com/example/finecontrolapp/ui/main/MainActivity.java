package com.example.finecontrolapp.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;

import com.example.finecontrolapp.ui.main.BudgetFragment;
import com.example.finecontrolapp.HomeFragment;
import com.example.finecontrolapp.ui.main.NewFragment;
import com.example.finecontrolapp.ProfileFragment;
import com.example.finecontrolapp.R;
import com.example.finecontrolapp.databinding.NavBarMenuBinding;
import com.example.finecontrolapp.ui.main.TransactionsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        NavBarMenuBinding binding = NavBarMenuBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        Intent intent = getIntent();
//        String user = intent.getStringExtra("user");
//
//        if (savedInstanceState == null) {
//            HomeFragment homeFragment = HomeFragment.newInstance(user);
//            replaceFragment(homeFragment);
//        }
//
//        binding.btnHome.setOnClickListener(v -> {
//            HomeFragment homeFragment = HomeFragment.newInstance(user);
//            replaceFragment(homeFragment);
//        });
//
//        binding.btnTransactions.setOnClickListener(v -> replaceFragment(new TransactionsFragment()));
//
//        binding.btnNew.setOnClickListener(v -> replaceFragment(new NewFragment()));
//
//        binding.btnBudget.setOnClickListener(v -> replaceFragment(new BudgetFragment()));
//
//        binding.btnProfile.setOnClickListener(v -> replaceFragment(new ProfileFragment()));
//
//    }
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_main);
    NavController navController = navHostFragment.getNavController();


    BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
//    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_main);
    NavigationUI.setupWithNavController(bottomNav, navController);
}

//    private void replaceFragment(Fragment fragment) {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.containerMain, fragment);
//        transaction.commit();
//    }
}
