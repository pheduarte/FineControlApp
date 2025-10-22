package com.example.finecontrolapp.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finecontrolapp.R;
import com.example.finecontrolapp.databinding.FragmentTransactionsBinding;
import com.example.finecontrolapp.ui.main.data.TransactionAdapter;
import com.example.finecontrolapp.ui.main.data.Transactions;


public class  TransactionsFragment extends Fragment {

    private FragmentTransactionsBinding binding;

    public static TransactionsFragment newInstance() { return new TransactionsFragment(); }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentTransactionsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize RecyclerView and its adapter
        RecyclerView recyclerView = view.findViewById(R.id.recyclerTransactions);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        TransactionAdapter adapter = new TransactionAdapter();
        recyclerView.setAdapter(adapter);

        TransactionsViewModel viewModel = new ViewModelProvider(this).get(TransactionsViewModel.class);

        // Captures logged in user's email to link with transactions
        SharedPreferences prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String email = prefs.getString("logged_in_email", null);

        // Retrieves and displays transactions for the logged in user
        if (email != null) {
            viewModel.getTransactionsByUser(email)
                    .observe(getViewLifecycleOwner(), adapter::setTransactions);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
