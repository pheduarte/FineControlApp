package com.example.finecontrolapp.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finecontrolapp.R;
import com.example.finecontrolapp.databinding.FragmentBudgetBinding;
import com.example.finecontrolapp.databinding.FragmentHomeBinding;
import com.example.finecontrolapp.ui.main.data.BudgetItem;
import com.example.finecontrolapp.ui.main.profile.SettingItem;

import java.util.ArrayList;
import java.util.List;


public class BudgetFragment extends Fragment {

    private FragmentBudgetBinding binding;

    public static BudgetFragment newInstance() { return new BudgetFragment(); }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentBudgetBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.recyclerBudgetList.setLayoutManager(new LinearLayoutManager(getContext()));

        List<BudgetItem> budgetList = new ArrayList<>();

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

        BudgetAdapter adapter = new BudgetAdapter(budgetList);
        binding.recyclerBudgetList.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
