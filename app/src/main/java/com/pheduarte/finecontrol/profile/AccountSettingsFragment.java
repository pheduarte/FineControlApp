package com.pheduarte.finecontrol.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.pheduarte.finecontrol.R;
import com.pheduarte.finecontrol.databinding.FragmentAccountSettingsBinding;
import com.pheduarte.finecontrol.login.LoginScreen;
import com.pheduarte.finecontrol.login.LoginViewModel;
import com.pheduarte.finecontrol.ui.main.TransactionsViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountSettingsFragment extends Fragment {

    private FragmentAccountSettingsBinding binding;

    private BottomSheetDialog progressSheet;

    private void showProgressSheet(String message) {
        // If the dialog hasn't been created yet
        if (progressSheet == null) {
            // Use the correct Material 3 theme overlay
            progressSheet = new BottomSheetDialog(requireContext(), com.google.android.material.R.style.ThemeOverlay_Material3_BottomSheetDialog);

            // Inflate the custom layout
            View sheetView = LayoutInflater.from(requireContext()).inflate(R.layout.bottomsheet_progress, null);

            // Set the inflated view as the content of the bottom sheet
            progressSheet.setContentView(sheetView);

            // Make it so the user cannot dismiss it by swiping or tapping outside
            progressSheet.setCancelable(false);
        }

        // --- CORRECTION ---
        // Find the TextView within the bottom sheet's content view
        TextView messageView = progressSheet.findViewById(R.id.progressMessage);

        // Set the message on the TextView
        if (messageView != null) {
            messageView.setText(message);
        }

        // Show the dialog
        progressSheet.show();
    }


    private void hideProgressSheet() {
        if (progressSheet != null && progressSheet.isShowing()) {
            progressSheet.dismiss();
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAccountSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        binding.btnDeleteAccount.setOnClickListener(v -> {

            LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
            TransactionsViewModel transactionsViewModel = new ViewModelProvider(this).get(TransactionsViewModel.class);


            SharedPreferences prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            String email = prefs.getString("logged_in_email", null);

            new AlertDialog.Builder(requireContext())
                    .setTitle("Delete my account")
                    .setMessage("Are you sure you want to delete your account? This action cannot be undone.")
                    .setPositiveButton("Delete", (dialog, which) -> {

                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                        showProgressSheet("Deleting your account...");

                        if (currentUser != null) {
                            currentUser.delete().addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    transactionsViewModel.deleteAllTransactionsByUser(email);
                                    loginViewModel.deleteUser(email, () -> {
                                        hideProgressSheet();

                                        Toast.makeText(getContext(), "Account deleted successfully.", Toast.LENGTH_SHORT).show();

                                        FirebaseAuth.getInstance().signOut();

                                        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                                .requestIdToken(getString(R.string.default_web_client_id))
                                                .requestEmail()
                                                .build();
                                        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(requireContext(), gso);
                                        googleSignInClient.signOut();

                                        Intent intent = new Intent(requireContext(), LoginScreen.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        requireActivity().finish();
                                    });
                                } else {
                                    hideProgressSheet();
                                    Toast.makeText(getContext(), "Failed to delete Firebase account.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            loginViewModel.deleteUser(email, () -> {
                                hideProgressSheet();
                                Toast.makeText(getContext(), "Local account deleted.", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(requireContext(), LoginScreen.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                requireActivity().finish();
                            });
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
