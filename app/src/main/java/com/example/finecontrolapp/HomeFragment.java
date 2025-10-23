package com.example.finecontrolapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.finecontrolapp.databinding.FragmentHomeBinding;
import com.example.finecontrolapp.ui.main.TransactionsViewModel;
import com.example.finecontrolapp.ui.main.data.TransactionAdapter;
import com.example.finecontrolapp.ui.main.login.MainActivityViewModel;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private static final String ARG_USER = "user";

    public static HomeFragment newInstance(String user) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivityViewModel mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);

        // Get logged-in email from SharedPreferences
        SharedPreferences prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String email = prefs.getString("logged_in_email", null);

        if (email != null) {
            // Fetch the first name from Room via ViewModel
            mainActivityViewModel.getFirstName(email).observe(getViewLifecycleOwner(), fName -> {
                if (fName != null) {
                    binding.hiUserName.setText(String.format("Hi, %s!", fName));

                        if (fName.length() > 15) {
                            binding.hiUserName.setTextSize(25);
                        }

                } else {
                    binding.hiUserName.setText(R.string.hi_userName);
                }
            });

            // Set click listener for profile icon and navigate to Profile Screen
            binding.profileIconHeader.setOnClickListener(v -> {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.containerMain, new ProfileFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            });
        }

        // Initialize RecyclerView and its adapter
        RecyclerView recyclerView = view.findViewById(R.id.recyclerTransactions);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        TransactionAdapter adapter = new TransactionAdapter();
        recyclerView.setAdapter(adapter);

        TransactionsViewModel viewModel = new ViewModelProvider(this).get(TransactionsViewModel.class);

        // Retrieves and displays transactions for the logged in user
        if (email != null) {
            viewModel.getTransactionsByUser(email)
                    .observe(getViewLifecycleOwner(), adapter::setTransactions);
        }
    }

    // Destroy binding
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
