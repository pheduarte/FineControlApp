package com.example.finecontrolapp.ui.main.profile;

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
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finecontrolapp.R;
import com.example.finecontrolapp.databinding.FragmentProfileBinding;
import com.example.finecontrolapp.ui.main.TransactionsViewModel;
import com.example.finecontrolapp.ui.main.data.TransactionAdapter;
import com.example.finecontrolapp.ui.main.login.LoginScreen;

import java.util.ArrayList;
import java.util.List;


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

        // Clears all users transactions
        binding.btnClearTransactions.setOnClickListener( v -> {

            TransactionsViewModel viewModel = new ViewModelProvider(this).get(TransactionsViewModel.class);

            // Get current user's email
            SharedPreferences prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            String email = prefs.getString("logged_in_email", null);

            // Show confirmation dialog
            new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    .setTitle("Clear all transactions")
                    .setMessage("Are you sure you want to delete all transactions?")
                    .setPositiveButton("Yes", (dialog, which) -> {

                        // For current user
                        viewModel.deleteAllByUser(email);

                        Toast.makeText(getContext(), "All transactions deleted.", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });


        // Logs user out and clears shared preferences
        binding.btnLogout.setOnClickListener( v -> {

            SharedPreferences prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            prefs.edit().clear().apply();

            Intent intent = new Intent(requireContext(), LoginScreen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            requireActivity().finish();
        });


            binding.recyclerSettings.setLayoutManager(new LinearLayoutManager(getContext()));

            List<SettingItem> settings = new ArrayList<>();

        settings.add(new SettingItem(
                R.drawable.icon_home, "Account settings", "Manage your account preferences",
                () -> NavHostFragment.findNavController(this).navigate(R.id.action_profileFragment_to_accountSettingsFragment)
        ));

        settings.add(new SettingItem(
                R.drawable.icon_home, "Notifications", "Configure notifications settings",
                () -> NavHostFragment.findNavController(this).navigate(R.id.action_profileFragment_to_notificationsFragment)
        ));

        settings.add(new SettingItem(
                R.drawable.icon_home, "Privacy & security", "Manage your privacy and security settings",
                () -> NavHostFragment.findNavController(this).navigate(R.id.action_profileFragment_to_privacySecurityFragment)
        ));

        settings.add(new SettingItem(
                R.drawable.icon_home, "Help & support", "Get help and support",
                () -> NavHostFragment.findNavController(this).navigate(R.id.action_profileFragment_to_helpSupportFragment)
        ));

        SettingsAdapter adapter = new SettingsAdapter(settings);
        binding.recyclerSettings.setAdapter(adapter);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
