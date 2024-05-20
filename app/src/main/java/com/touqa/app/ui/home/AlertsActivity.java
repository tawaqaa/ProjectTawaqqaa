package com.touqa.app.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.touqa.app.R;
import com.touqa.app.adapters.ReportAdapter;
import com.touqa.app.base.BaseActivity;
import com.touqa.app.databinding.ActivityAlertsBinding;
import com.touqa.app.model.Report;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlertsActivity extends BaseActivity {

    private ActivityAlertsBinding binding;
    private View previousView;
    private ReportAdapter adapter ;

    List<Report> normalReports = new ArrayList<>();
    List<Report> allReports = new ArrayList<>();
    List<Report> dangerReports = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_alerts);

        previousView = binding.all;
        setupListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        retrieveReports();

    }

    private void setupListener() {
        binding.all.setOnClickListener(v -> {
            changeColor(v);
            adapter = new ReportAdapter(allReports,this);
            binding.recycler.setAdapter(adapter);
        });
        binding.normal.setOnClickListener(v -> {
            changeColor(v);
            adapter = new ReportAdapter(normalReports,this);
            binding.recycler.setAdapter(adapter);
        });
        binding.danger.setOnClickListener(v -> {
            changeColor(v);
            adapter = new ReportAdapter(dangerReports,this);
            binding.recycler.setAdapter(adapter);
        });


    }

    private void changeColor(View currentView) {

        previousView.setBackground(getDrawable(R.drawable.corner_four2));
        TextView previousTextView = (TextView) previousView;
        previousTextView.setTextColor(Color.BLACK);

        previousView = currentView;
        currentView.setBackground(getDrawable(R.drawable.corner_border_blue));
        TextView currentTextView = (TextView) previousView;
        currentTextView.setTextColor(Color.WHITE);
    }



    private void retrieveReports() {
        showProgressDialog();
        DatabaseReference reportsRef = FirebaseDatabase.getInstance().getReference("Reports");
        reportsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Report> reports = new ArrayList<>();
                Map<String, Integer> nameCountMap = new HashMap<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Report report = snapshot.getValue(Report.class);
                    String name = report.getNameStreet();

                    // Check if report with same name already exists
                    if (nameCountMap.containsKey(name)) {
                        // Increment count if exists
                        int count = nameCountMap.get(name);
                        nameCountMap.put(name, count + 1);
                    } else {
                        nameCountMap.put(name, 1);
                        reports.add(report);
                    }
                }

                for (Report report : reports) {
                    int count = nameCountMap.get(report.getNameStreet());
                    if (count > 3) {
                        report.setStatus("خطر");
                        dangerReports.add(report);
                        allReports.add(report);
                    } else {
                        report.setStatus("متوسط");
                        normalReports.add(report);
                        allReports.add(report);
                    }
                }
                adapter = new ReportAdapter(reports,AlertsActivity.this);
                binding.recycler.setAdapter(adapter);
                binding.data.setVisibility(View.VISIBLE);
                dismissProgressDialog();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                dismissProgressDialog();
            }
        });
    }




}