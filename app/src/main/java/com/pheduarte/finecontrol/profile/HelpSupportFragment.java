package com.pheduarte.finecontrol.profile;

import static android.app.ProgressDialog.show;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pheduarte.finecontrol.R;
import com.pheduarte.finecontrol.databinding.FragmentHelpSupportBinding;

public class HelpSupportFragment extends Fragment {
    private FragmentHelpSupportBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHelpSupportBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Goes back to profile fragment
        binding.btnBackSupport.setOnClickListener( v -> {

            NavHostFragment.findNavController(HelpSupportFragment.this)
                    .navigate(R.id.action_helpSupportFragment_to_profileFragment);
        });

        // Opens mail app to send support email
        binding.linkEmailSupport.setOnClickListener( V -> {
            String email = getString(R.string.support_email);

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:" + email));

            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.finecontrol_support));

            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getContext(), R.string.no_email_app_found, Toast.LENGTH_SHORT).show();
            }

        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}