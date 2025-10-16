package com.example.finecontrolapp.ui.main.login;

import androidx.lifecycle.ViewModel;

import com.example.finecontrolapp.databinding.FragmentSignUpBinding;
import com.example.finecontrolapp.ui.main.data.User;

public class SignUpViewModel extends ViewModel {
    private FragmentSignUpBinding binding;

    public User GetUserDetails() {
        String fName = binding.txtFirstName.getText().toString();
        String lName = binding.txtLastName.getText().toString();
        String email = binding.txtEmail.getText().toString();
        int password = Integer.parseInt(binding.txtPassword.getText().toString());
        String phoneNumber = binding.txtPhoneNumber.getText().toString();

        return new User(fName, lName, email, password, phoneNumber);
    }
}