package com.example.finecontrolapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.finecontrolapp.databinding.FragmentProfileBinding;
import com.example.finecontrolapp.ui.main.MainActivity;
import com.example.finecontrolapp.ui.main.TransactionsViewModel;
import com.example.finecontrolapp.ui.main.login.LoginScreen;
import com.example.finecontrolapp.ui.main.login.LoginScreenFragment;


public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;


    public static ProfileFragment newInstance() { return new ProfileFragment(); }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnClearTransactions.setOnClickListener( v -> {

            TransactionsViewModel viewModel = new ViewModelProvider(this).get(TransactionsViewModel.class);

            SharedPreferences prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            String email = prefs.getString("logged_in_email", null);

            new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    .setTitle("Clear all transactions")
                    .setMessage("Are you sure you want to delete all transactions?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        // For all users:
                        // viewModel.deleteAll();

                        // For this user only:
                        viewModel.deleteAllByUser(email);

                        Toast.makeText(getContext(), "All transactions deleted.", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });


        binding.btnLogout.setOnClickListener( v -> {

            SharedPreferences prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            prefs.edit().clear().apply();

            Intent intent = new Intent(requireContext(), LoginScreen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            requireActivity().finish();

        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
