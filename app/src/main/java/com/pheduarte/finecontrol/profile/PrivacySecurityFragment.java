package com.pheduarte.finecontrol.profile;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pheduarte.finecontrol.R;
import com.pheduarte.finecontrol.databinding.FragmentPrivacySecurityBinding;
import androidx.browser.customtabs.CustomTabsIntent;


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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnBackPrivacy.setOnClickListener( v -> {

            NavHostFragment.findNavController(PrivacySecurityFragment.this)
                    .navigate(R.id.action_privacySecurityFragment_to_profileFragment);
        });

        binding.linkPolicy.setOnClickListener( V -> {
            String url = "https://sites.google.com/view/finecontrolappprivacypolicy";
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(getActivity(), Uri.parse(url));
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}