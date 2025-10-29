package com.example.finecontrolapp.profile;

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

import com.example.finecontrolapp.R;
import com.example.finecontrolapp.databinding.FragmentProfileBinding;
import com.example.finecontrolapp.ui.main.TransactionsViewModel;
import com.example.finecontrolapp.login.LoginScreen;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

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
                        viewModel.deleteAllTransactionsByUser(email);

                        Toast.makeText(getContext(), "All transactions deleted.", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });


        // Logs user out and clears shared preferences
        binding.btnLogout.setOnClickListener( v -> {

            // Sign out from Firebase
            FirebaseAuth.getInstance().signOut();

            // Also sign out from Google
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(requireContext(), gso);
            googleSignInClient.signOut();

            SharedPreferences prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            prefs.edit().clear().apply();

            Intent intent = new Intent(requireContext(), LoginScreen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            requireActivity().finish();
        });

        //
        assert binding.recyclerSettings != null;
        binding.recyclerSettings.setLayoutManager(new LinearLayoutManager(getContext()));

        List<SettingItem> settings = new ArrayList<>();

        settings.add(new SettingItem(
                R.drawable.icon_setting, "Account settings", "Manage your account preferences",
                () -> NavHostFragment.findNavController(this).navigate(R.id.action_profileFragment_to_accountSettingsFragment)
        ));

        settings.add(new SettingItem(
                R.drawable.icon_notifications, "Notifications", "Configure notifications settings",
                () -> NavHostFragment.findNavController(this).navigate(R.id.action_profileFragment_to_notificationsFragment)
        ));

        settings.add(new SettingItem(
                R.drawable.icon_secure, "Privacy & security", "Manage your privacy and security settings",
                () -> NavHostFragment.findNavController(this).navigate(R.id.action_profileFragment_to_privacySecurityFragment)
        ));

        settings.add(new SettingItem(
                R.drawable.icon_support, "Help & support", "Get help and support",
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
