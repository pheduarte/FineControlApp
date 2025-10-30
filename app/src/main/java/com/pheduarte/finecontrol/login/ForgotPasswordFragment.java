package com.pheduarte.finecontrol.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pheduarte.finecontrol.R;
import com.pheduarte.finecontrol.databinding.FragmentForgotPasswordBinding;


public class ForgotPasswordFragment extends Fragment {
    private LoginViewModel loginViewModel;
    private FragmentForgotPasswordBinding binding;

    public static ForgotPasswordFragment newInstance() {return new ForgotPasswordFragment();}


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Safe to get ViewModels here
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnChangePassword.setOnClickListener( v -> {
            String newPassword = binding.textNewPassword.getText().toString();
            String confirmPassword = binding.textReEnterPassword.getText().toString();

            if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                binding.textNewPassword.setError("Field cannot be empty");
                binding.textReEnterPassword.setError("Field cannot be empty");}

            if (!newPassword.equals(confirmPassword)) {
                binding.textNewPassword.setError("Passwords do not match");
                binding.textReEnterPassword.setError("Passwords do not match");}

            else {
                // Insert the logic to change password in DB

                NavHostFragment.findNavController(ForgotPasswordFragment.this)
                        .navigate(R.id.action_forgotPasswordFragment_to_loginScreenFragment);
            }

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}