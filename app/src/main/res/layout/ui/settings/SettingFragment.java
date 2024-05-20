package com.mnashat_dev.touqa.ui.settings;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.mnashat_dev.touqa.auth.AuthActivity;
import com.mnashat_dev.touqa.databinding.FragmentSettingsBinding;
import com.touqa.app.ui.settings.EditEmailActivity;
import com.touqa.app.ui.settings.EditPassActivity;
import com.touqa.app.ui.settings.EditPhoneActivity;


public class SettingFragment extends Fragment {

    private FragmentSettingsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        setupListener();
        return binding.getRoot();
    }

    private void setupListener() {
        binding.logout.setOnClickListener(v -> showLogoutConfirmationDialog());
        binding.name.setOnClickListener(v -> startActivity(new Intent(requireActivity(), EditNameActivity.class)));
        binding.email.setOnClickListener(v -> startActivity(new Intent(requireActivity(), EditEmailActivity.class)));
        binding.pass.setOnClickListener(v -> startActivity(new Intent(requireActivity(), EditPassActivity.class)));
        binding.phone.setOnClickListener(v -> startActivity(new Intent(requireActivity(), EditPhoneActivity.class)));
        binding.help.setOnClickListener(v -> startActivity(new Intent(requireActivity(), HelpingActivity.class)));
        binding.aboutApp.setOnClickListener(v -> startActivity(new Intent(requireActivity(), AboutAppActivity.class)));
        binding.terms.setOnClickListener(v -> startActivity(new Intent(requireActivity(), TermsActivity.class)));
    }

    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("تسجيل الخروج");
        builder.setMessage("هل أنت متأكد من تسجيل الخروج؟");
        builder.setPositiveButton("نعم", (dialog, which) -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(requireActivity(), AuthActivity.class));
            requireActivity().finish();
        });
        builder.setNegativeButton("لا", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

}