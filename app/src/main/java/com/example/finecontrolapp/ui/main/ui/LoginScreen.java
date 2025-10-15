package com.example.finecontrolapp.ui.main.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finecontrolapp.databinding.ActivityLoginScreenBinding;

public class LoginScreen extends AppCompatActivity {

    private ActivityLoginScreenBinding binding; // ViewBinding class generated from activity_login.xml

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(v -> {
            String user = binding.etUsername.getText().toString().trim();
            String pass = binding.etPassword.getText().toString().trim();

            // super simple placeholder validation
            if (user.isEmpty()) {
                binding.etUsername.setError("Required");
                return;
            }
            if (pass.isEmpty()) {
                binding.etPassword.setError("Required");
                return;
            }

            if (user.equals("admin") && pass.equals("1234")) {
                Toast.makeText(this, "Welcome!", Toast.LENGTH_SHORT).show();
                // TODO: later navigate to MainActivity (home with bottom nav)
                // startActivity(new Intent(this, MainActivity.class));
                // finish();
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
