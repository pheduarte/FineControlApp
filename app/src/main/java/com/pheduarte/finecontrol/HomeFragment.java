package com.pheduarte.finecontrol;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pheduarte.finecontrol.databinding.FragmentHomeBinding;
import com.pheduarte.finecontrol.ui.main.DailyNotificationWorker;
import com.pheduarte.finecontrol.ui.main.TransactionsViewModel;
import com.pheduarte.finecontrol.data.TransactionAdapter;
import com.pheduarte.finecontrol.login.MainActivityViewModel;

import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

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

        // Navigates to New Fragment
        binding.fabAction.setOnClickListener(v -> {
            NavHostFragment.findNavController(HomeFragment.this)
                    .navigate(R.id.action_homeFragment_to_newFragment);
        });


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

            // Fetch the total amount from Room via ViewModel
            mainActivityViewModel.getTotalAmount(email).observe(getViewLifecycleOwner(), amount -> {
                if (amount == null || amount == 0.0) amount = 0.0;

                String formattedTotal = String.format(Locale.getDefault(), "$%.2f", Math.abs(amount));
                binding.txtAmount.setTextColor(ContextCompat.getColor(requireContext(), R.color.black_overlay));

                if (amount > 0) {
                    binding.txtAmount.setText(formattedTotal);
                    binding.txtAmount.setTextColor(ContextCompat.getColor(requireContext(), R.color.greenIncome));
                } else if (amount < 0) {
                    binding.txtAmount.setText("-" + formattedTotal);
                    binding.txtAmount.setTextColor(ContextCompat.getColor(requireContext(), R.color.redExpense));
                } else {
                    binding.txtAmount.setTextColor(ContextCompat.getColor(requireContext(), R.color.black_overlay));
                }
            });

            // Set click listener for profile icon and navigate to Profile Screen
            binding.profileIconHeader.setOnClickListener(v -> {
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.action_homeFragment_to_profileFragment);
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
            viewModel.getTransactionsByUser(email).observe(getViewLifecycleOwner(), transactions -> {
                if (transactions == null || transactions.isEmpty()) {
                    binding.recyclerTransactions.setVisibility(View.GONE);
                    binding.textEmptyList.setVisibility(View.VISIBLE);
                } else {
                    binding.recyclerTransactions.setVisibility(View.VISIBLE);
                    binding.textEmptyList.setVisibility(View.GONE);
                    adapter.setTransactions(transactions);
                }
            });
        }

        // Checks if notifications are on and triggers it
        boolean enabled = prefs.getBoolean("notifications_enabled", true);

        if (enabled) {
        scheduleDailyNotification(requireContext());
        }


    }

    // Schedule daily notification
    public void scheduleDailyNotification(Context context) {

        Calendar now = Calendar.getInstance();
        Calendar target = Calendar.getInstance();
        target.set(Calendar.HOUR_OF_DAY, 10);
        target.set(Calendar.MINUTE, 0);
        target.set(Calendar.SECOND, 0);

        if (target.before(now)) {
            target.add(Calendar.DAY_OF_YEAR, 1);
        }

        long initialDelay = target.getTimeInMillis() - now.getTimeInMillis();

        PeriodicWorkRequest request =
                new PeriodicWorkRequest.Builder(DailyNotificationWorker.class, 24, TimeUnit.HOURS)
                        .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
                        .build();


        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "daily_notification",
                ExistingPeriodicWorkPolicy.UPDATE,
                request
        );
    }


    // Destroy binding
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

