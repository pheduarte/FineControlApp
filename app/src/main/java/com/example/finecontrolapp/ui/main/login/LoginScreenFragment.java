package com.example.finecontrolapp.ui.main.login;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.navigation.fragment.NavHostFragment;
import com.example.finecontrolapp.R;
import com.example.finecontrolapp.databinding.FragmentLoginScreenBinding;
import com.example.finecontrolapp.ui.main.MainActivity;

public class LoginScreenFragment extends Fragment {

    private LoginViewModel mViewModel;

    private FragmentLoginScreenBinding binding;

    public static LoginScreenFragment newInstance() {
        return new LoginScreenFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginScreenBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.linkSignUp.setOnClickListener(v -> {
            NavHostFragment.findNavController(LoginScreenFragment.this)
                    .navigate(R.id.action_loginScreenFragment_to_signUpFragment);
        });

        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.etUsername.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();

            // super simple placeholder validation
            if (email.isEmpty()) {
                binding.etUsername.setError("Required");
                return;
            }
            if (password.isEmpty()) {
                binding.etPassword.setError("Required");
                return;
            }

            mViewModel.verifyUser(email, password, isValid -> {
                if (isValid) {
                    Toast.makeText(getContext(), "Welcome!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(requireContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    requireActivity().finish();

                    // close the LoginScreen Activity so user can’t go back
                    requireActivity().finish();
                } else {
                    Toast.makeText(getContext(), "Invalid credentials!", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}