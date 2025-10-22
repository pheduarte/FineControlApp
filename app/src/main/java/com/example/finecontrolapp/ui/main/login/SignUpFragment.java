package com.example.finecontrolapp.ui.main.login;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.finecontrolapp.R;
import com.example.finecontrolapp.databinding.FragmentSignUpBinding;
import com.example.finecontrolapp.ui.main.MainActivity;
import com.example.finecontrolapp.ui.main.data.User;

public class SignUpFragment extends Fragment {

    private LoginViewModel loginViewModel;
    private SignUpViewModel signUpViewModel;
    private FragmentSignUpBinding binding;

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Safe to get ViewModels here
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        signUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnSignUp.setOnClickListener(v -> {
            try {
                // Collect user details
                String fName = binding.txtFirstName.getText().toString().trim();
                String lName = binding.txtLastName.getText().toString().trim();
                String email = binding.txtEmail.getText().toString().trim();
                String password = binding.txtPassword.getText().toString().trim();
                String phoneText = binding.txtPhoneNumber.getText().toString().trim();

                if (fName.isEmpty() || lName.isEmpty() || email.isEmpty() ||
                        password.isEmpty() || phoneText.isEmpty()) {
                    Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                int phoneNumber = Integer.parseInt(phoneText);

                User newUser = new User(fName, lName, email, phoneNumber, password);

                loginViewModel.getUser(email).observe(getViewLifecycleOwner(), new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        if (user != null) {
                            Toast.makeText(getContext(), "User already exists, sign in instead", Toast.LENGTH_SHORT).show();

                        } else {
                            loginViewModel.register(newUser);

                            new android.os.Handler().postDelayed(() -> {
                                Toast.makeText(getContext(), "User Registered Successfully!", Toast.LENGTH_SHORT).show();

                                // Save user details to SharedPreferences
                                SharedPreferences prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                                prefs.edit().putString("logged_in_email", email).apply();

                                Intent intent = new Intent(requireContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                requireActivity().finish();
                            }, 400);
                    }
                        loginViewModel.getUser(email).removeObserver(this);
                    }
                });

            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Invalid phone number", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
