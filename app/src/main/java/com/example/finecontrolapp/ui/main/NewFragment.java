package com.example.finecontrolapp.ui.main;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.finecontrolapp.R;
import com.example.finecontrolapp.databinding.FragmentHomeBinding;
import com.example.finecontrolapp.databinding.FragmentNewBinding;
import com.example.finecontrolapp.ui.main.data.Transactions;

import java.util.Calendar;


public class NewFragment extends Fragment {

    private FragmentNewBinding binding;


    public static NewFragment newInstance() { return new NewFragment(); }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentNewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TransactionsViewModel viewModel = new ViewModelProvider(this).get(TransactionsViewModel.class);

        SharedPreferences prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String email = prefs.getString("logged_in_email", null);

        final String[] type = {"Expense"};
        final String[] date = {""};


        binding.btnNewExpense.setOnClickListener(v -> {
            type[0] = "Expense";
            highlightSelectedButton(binding.btnNewExpense, binding.btnNewIncome);
        });

        binding.btnNewIncome.setOnClickListener(v -> {
            type[0] = "Income";
            highlightSelectedButton(binding.btnNewIncome, binding.btnNewExpense);
        });

        binding.btnSelectDate.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),
                    (datePickerView, selectedYear, selectedMonth, selectedDay) -> {
                        // Month is 0-based, so add +1
                        String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;

                        // Display it
                        binding.tvSelectedDate.setText(selectedDate);

                        date[0] = selectedDate;
                    },
                    year, month, day
            );

            datePickerDialog.show();
        });


        binding.btnNewTransaction.setOnClickListener(v -> {
            String description;
            String amount;
            String category;

            description = binding.newDescription.getText().toString();
            amount = binding.newAmount.getText().toString();
            category = "Teste";

            String transactionType = type[0];
            String transactionDate = date[0];

            if (description.isEmpty() || amount.isEmpty() ||
                    transactionDate.isEmpty() || transactionType.isEmpty()) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double tempDouble = Double.parseDouble(amount);

                viewModel.insert(new Transactions(description, transactionType, tempDouble, category, transactionDate, email));
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Invalid amount", Toast.LENGTH_SHORT).show();
                return;
            }

            binding.newDescription.setText("");
            binding.newAmount.setText("");
            binding.tvSelectedDate.setText("");
            highlightSelectedButton(binding.btnNewExpense, binding.btnNewIncome);

            Toast.makeText(getContext(), "Transaction added", Toast.LENGTH_SHORT).show();
        });

    }

    private void highlightSelectedButton(com.google.android.material.button.MaterialButton selectedBtn,
                                         com.google.android.material.button.MaterialButton unselectedBtn) {

        if (selectedBtn == binding.btnNewIncome) {
            // Highlight selected button
            selectedBtn.setBackgroundTintList(android.content.res.ColorStateList.valueOf(
                    getResources().getColor(R.color.greenIncome, null)
            ));
            selectedBtn.setStrokeColor(android.content.res.ColorStateList.valueOf(
                    getResources().getColor(R.color.greenIncome, null)
            ));
            selectedBtn.setTextColor(getResources().getColor(android.R.color.white, null));

            // Reset unselected button
            unselectedBtn.setBackgroundTintList(android.content.res.ColorStateList.valueOf(
                    getResources().getColor(R.color.neutralGray, null)
            ));
            unselectedBtn.setStrokeColor(android.content.res.ColorStateList.valueOf(
                    getResources().getColor(R.color.neutralGray, null)
            ));
            unselectedBtn.setTextColor(getResources().getColor(android.R.color.black, null));
        } else {
            // Highlight selected button
            selectedBtn.setBackgroundTintList(android.content.res.ColorStateList.valueOf(
                    getResources().getColor(R.color.redExpense, null)
            ));
            selectedBtn.setStrokeColor(android.content.res.ColorStateList.valueOf(
                    getResources().getColor(R.color.redExpense, null)
            ));
            selectedBtn.setTextColor(getResources().getColor(android.R.color.white, null));

            // Reset unselected button
            unselectedBtn.setBackgroundTintList(android.content.res.ColorStateList.valueOf(
                    getResources().getColor(R.color.neutralGray, null)
            ));
            unselectedBtn.setStrokeColor(android.content.res.ColorStateList.valueOf(
                    getResources().getColor(R.color.neutralGray, null)
            ));
            unselectedBtn.setTextColor(getResources().getColor(android.R.color.black, null));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
