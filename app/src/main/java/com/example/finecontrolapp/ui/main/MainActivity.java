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


@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_main);
    NavController navController = navHostFragment.getNavController();


    BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
    NavigationUI.setupWithNavController(bottomNav, navController);
}

}
