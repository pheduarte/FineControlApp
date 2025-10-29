package com.example.finecontrolapp.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finecontrolapp.R;
import com.example.finecontrolapp.databinding.FragmentNotificationsBinding;
import com.example.finecontrolapp.databinding.FragmentPrivacySecurityBinding;


public class PrivacySecurityFragment extends Fragment {
    private FragmentPrivacySecurityBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPrivacySecurityBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}