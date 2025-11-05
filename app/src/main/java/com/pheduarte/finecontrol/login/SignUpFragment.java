package com.pheduarte.finecontrol.login;

import static kotlin.text.Typography.tm;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.telephony.TelephonyManager;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.pheduarte.finecontrol.databinding.FragmentSignUpBinding;
import com.pheduarte.finecontrol.ui.main.MainActivity;
import com.pheduarte.finecontrol.data.User;

import java.util.Locale;


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
                String fName = binding.txtFirstName.getText().toString().trim();
                String lName = binding.txtLastName.getText().toString().trim();
                String email = binding.txtEmail.getText().toString().trim();
                String tempPassword = binding.txtPassword.getText().toString().trim();
                String confirmedPassword = binding.txtPassword2.getText().toString().trim();
                String phoneText = binding.txtPhoneNumber.getText().toString().trim();


                boolean isValid = PhoneValidator.isValidPhoneNumber(getContext(), phoneText);
                if (!isValid) {
                    binding.txtPhoneNumber.setError("Invalid phone number");
                    binding.txtPhoneNumber.requestFocus();
                    return;
                }

                if (fName.isEmpty() || lName.isEmpty() || email.isEmpty() ||
                        tempPassword.isEmpty() || phoneText.isEmpty()) {
                    Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.txtEmail.setError("Please enter a valid email address");
                    binding.txtEmail.requestFocus();
                    return;
                }

                if (!tempPassword.equals(confirmedPassword)) {
                    binding.txtPassword2.setError("Passwords do not match");
                    binding.txtPassword2.requestFocus();
                    return;
                }

                String password = tempPassword;
                User newUser = new User(fName, lName, email, phoneText, password);

                // --- background thread for one-time synchronous query ---
                new Thread(() -> {
                    boolean exists = loginViewModel.getUserNow(email);

                    requireActivity().runOnUiThread(() -> {
                        if (exists) {
                            Toast.makeText(getContext(),
                                    "User already exists, sign in instead",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            loginViewModel.register(newUser);
                            Toast.makeText(getContext(),
                                    "User Registered Successfully!",
                                    Toast.LENGTH_SHORT).show();

                            SharedPreferences prefs = requireContext()
                                    .getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                            prefs.edit().putString("logged_in_email", email).apply();

                            Intent intent = new Intent(requireContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            requireActivity().finish();
                        }
                    });
                }).start();

            } catch (Exception e) {
                Toast.makeText(getContext(), "Invalid input: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public boolean isValidPhoneNumber(String number, String countryCode) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber parsedNumber = phoneUtil.parse(number, countryCode);
            return phoneUtil.isValidNumber(parsedNumber);
        } catch (NumberParseException e) {
            return false;
        }
    }
}