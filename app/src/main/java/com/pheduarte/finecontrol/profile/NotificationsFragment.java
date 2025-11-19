package com.pheduarte.finecontrol.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.pheduarte.finecontrol.R;
import com.pheduarte.finecontrol.databinding.FragmentNotificationsBinding;
import com.pheduarte.finecontrol.ui.main.DailyNotificationWorker;

import java.util.concurrent.TimeUnit;


public class NotificationsFragment extends Fragment {
    private FragmentNotificationsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SwitchMaterial switchNotifications = view.findViewById(R.id.switchNotifications);

        SharedPreferences prefs = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        boolean enabled = prefs.getBoolean("notifications_enabled", true);

        // Show saved state
        switchNotifications.setChecked(enabled);

        // Toggle notifications on and off and shows a confirmation message
        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                enableDailyNotifications();
                Toast.makeText(getContext(),
                        "Notifications on",
                        Toast.LENGTH_SHORT).show();
            } else {
                disableDailyNotifications();
                Toast.makeText(getContext(),
                        "Notifications off",
                        Toast.LENGTH_SHORT).show();
            }

            prefs.edit().putBoolean("notifications_enabled", isChecked).apply();
        });

        // Allows user to go back to settings
        binding.btnBackNotifications.setOnClickListener(v -> {
            NavHostFragment.findNavController(NotificationsFragment.this)
                    .navigate(R.id.action_notificationsFragment_to_profileFragment);
        });
    }

    // Logic to toggle notifications on
    private void enableDailyNotifications() {
        Context context = requireContext();

        PeriodicWorkRequest request =
                new PeriodicWorkRequest.Builder(DailyNotificationWorker.class, 24, TimeUnit.HOURS)
                        .build();

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "daily_notification",
                ExistingPeriodicWorkPolicy.UPDATE,
                request
        );
    }

    // Logic to toggle notifications off
    private void disableDailyNotifications() {
        Context context = requireContext();
        WorkManager.getInstance(context).cancelUniqueWork("daily_notification");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}