package com.mnashat_dev.touqa.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.mnashat_dev.touqa.R;
import com.mnashat_dev.touqa.databinding.FragmentHomeBinding;
import com.touqa.app.ui.home.AlertsActivity;
import com.touqa.app.ui.home.DirectionsActivity;
import com.touqa.app.ui.home.MakeReportActivity;
import com.touqa.app.ui.home.SafetyActivity;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
      binding.textName.setText(retrieveUserName());
        binding.makeReport.setOnClickListener(v -> startActivity(new Intent(requireActivity(), MakeReportActivity.class)));
        binding.directions.setOnClickListener(v -> startActivity(new Intent(requireActivity(), DirectionsActivity.class)));
        binding.safety.setOnClickListener(v -> startActivity(new Intent(requireActivity(), SafetyActivity.class)));
        binding.allAlerts.setOnClickListener(v -> startActivity(new Intent(requireActivity(), AlertsActivity.class)));


        return binding.getRoot();
    }

    private String retrieveUserName() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        return sharedPreferences.getString("name", "");
    }
}