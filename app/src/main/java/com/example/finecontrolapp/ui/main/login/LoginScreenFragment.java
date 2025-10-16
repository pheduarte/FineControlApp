package com.example.finecontrolapp.ui.main.login;

import androidx.lifecycle.ViewModelProvider;
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
        // TODO: Use the ViewModel
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

            if (user.equals("phe") && pass.equals("1234")) {
                Toast.makeText(getContext(), "Welcome!", Toast.LENGTH_SHORT).show();

                NavHostFragment.findNavController(LoginScreenFragment.this)
                        .navigate(R.id.action_loginScreenFragment_to_mainActivity);
            } else {
                Toast.makeText(getContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}