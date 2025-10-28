package com.example.finecontrolapp.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.finecontrolapp.R;
import com.example.finecontrolapp.databinding.FragmentBudgetBinding;
import com.example.finecontrolapp.databinding.FragmentHomeBinding;
import com.example.finecontrolapp.ui.main.data.BudgetItem;
import com.example.finecontrolapp.ui.main.profile.SettingItem;

import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class BudgetFragment extends Fragment {

    private Calendar currentMonth;
    private FragmentBudgetBinding binding;
    private TransactionsViewModel viewModel;
    private String email;
    private BudgetAdapter adapter;
    private List<BudgetItem> budgetList;


    public static BudgetFragment newInstance() { return new BudgetFragment(); }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentBudgetBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(this).get(TransactionsViewModel.class);

        // Get logged-in email from SharedPreferences
        SharedPreferences prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        email = prefs.getString("logged_in_email", null);

        // Initialize the current month
        currentMonth = Calendar.getInstance();
        setupRecycler();
        updateMonthDisplay();

        binding.btnNextMonth.setOnClickListener( v -> {
            currentMonth.add(Calendar.MONTH, 1);
            updateMonthDisplay();
        });

        binding.btnPreviousMonth.setOnClickListener( v -> {
            currentMonth.add(Calendar.MONTH, -1);
            updateMonthDisplay();
        });

        return binding.getRoot();
    }

    // Update the month display with an specific pattern
    private void updateMonthDisplay() {
        SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy MMMM,", Locale.getDefault());
            binding.textMonthYear.setText(monthFormat.format(currentMonth.getTime()));

        viewModel.getMonthlyTotal(email, currentMonth)
                .observe(getViewLifecycleOwner(), total -> {
                    if (total == null) total = 0.0;
                    binding.totalSpent.setText(String.format(Locale.getDefault(), "$%.2f", total));
                });

        for (BudgetItem item : budgetList) {
            String category = item.getTitle();

            viewModel.getMonthlyByCategory(email, currentMonth, category)
                    .observe(getViewLifecycleOwner(), total -> {
                        if (total == null) total = 0.0;
                        adapter.updateAmountForCategory(category, String.format(Locale.getDefault(), "$%.2f", total));
                    });
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void setupRecycler() {
        binding.recyclerBudgetList.setLayoutManager(new LinearLayoutManager(getContext()));

        // Create a list of BudgetItem objects
        budgetList = new ArrayList<>();

        budgetList.add( new BudgetItem(R.drawable.icon_food, "Food", "0"));
        budgetList.add( new BudgetItem(R.drawable.icon_transport, "Transport", "0"));
        budgetList.add( new BudgetItem(R.drawable.icon_shopping, "Shopping", "0"));
        budgetList.add( new BudgetItem(R.drawable.icon_house, "House", "0"));
        budgetList.add( new BudgetItem(R.drawable.icon_subscription, "Subscription", "0"));
        budgetList.add( new BudgetItem(R.drawable.icon_bill, "Bills", "0"));
        budgetList.add( new BudgetItem(R.drawable.icon_health, "Health", "0"));
        budgetList.add( new BudgetItem(R.drawable.icon_salary, "Salary", "0"));
        budgetList.add( new BudgetItem(R.drawable.icon_transfer, "Transfer", "0"));
        budgetList.add( new BudgetItem(R.drawable.icon_more, "Other", "0"));

        adapter = new BudgetAdapter(budgetList);
        binding.recyclerBudgetList.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
