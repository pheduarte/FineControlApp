package com.example.finecontrolapp.ui.main.login;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.TokenWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.finecontrolapp.R;
import com.example.finecontrolapp.databinding.FragmentSignUpBinding;
import com.example.finecontrolapp.ui.main.data.User;

public class SignUpFragment extends Fragment {

    LoginViewModel viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    private SignUpViewModel mViewModel;
    private FragmentSignUpBinding binding;

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        // TODO: Use the ViewModel
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

            String fName = binding.txtFirstName.getText().toString();
            String lName = binding.txtLastName.getText().toString();
            String email = binding.txtEmail.getText().toString();
            int phoneNumber = Integer.parseInt(binding.txtPhoneNumber.getText().toString());
            String password = binding.txtPassword.getText().toString();

            viewModel.register(new User(fName, lName, email, phoneNumber, password));
            Toast.makeText(getContext(), "User Registered", Toast.LENGTH_SHORT).show();

            NavHostFragment.findNavController(SignUpFragment.this)
                    .navigate(R.id.action_signUpFragment_to_mainActivity);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}