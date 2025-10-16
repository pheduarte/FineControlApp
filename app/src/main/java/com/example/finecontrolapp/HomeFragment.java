package com.example.finecontrolapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.finecontrolapp.databinding.FragmentHomeBinding;

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

        if (getArguments() != null) {
            String user = getArguments().getString(ARG_USER);
            if ("phe".equals(user)) {
                binding.hiUserName.setText("Hi, Phelippe!");
            }
        }

        binding.profileIconHeader.setOnClickListener(v -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.containerMain, new ProfileFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
