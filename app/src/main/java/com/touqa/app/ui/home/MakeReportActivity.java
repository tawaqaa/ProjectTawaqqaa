package com.touqa.app.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.touqa.app.DoneActivity;
import com.touqa.app.R;
import com.touqa.app.base.BaseActivity;
import com.touqa.app.databinding.ActivityDirectionsBinding;
import com.touqa.app.databinding.ActivityMakeReportBinding;
import com.touqa.app.model.Report;
import com.touqa.app.util.Temp;


public class MakeReportActivity extends BaseActivity {
    private ActivityMakeReportBinding binding;
    private String nameStreet = "اختر اسم الشارع";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_make_report);
        setupSpinner();
        setupListener();

    }


    private void setupListener() {
        binding.btnSend.setOnClickListener(v -> {
            if (nameStreet.equals("اختر اسم الشارع") ){
                Toast.makeText(MakeReportActivity.this, "يرجى اختيار اسم الشارع اولا " , Toast.LENGTH_SHORT).show();
            }else {
                sendReport(nameStreet,getCurrentTimeFormatted() , "اﻹرتفاع 40 سم");
            }
        });

        binding.useMap.setOnClickListener(v -> {
                    startActivity(new Intent(this, SelectAddressActivity.class));
                    finish();
        }
        );

    }

    private  void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.street_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(adapter);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItem = parentView.getItemAtPosition(position).toString();
                nameStreet= selectedItem;
                if (!selectedItem.equals("اختر اسم الشارع")) {
                    Toast.makeText(MakeReportActivity.this, "إختيارك هو : " + selectedItem, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }


    private void sendReport(String nameStreet, String time, String height) {
        showProgressDialog();
        DatabaseReference reportsRef = FirebaseDatabase.getInstance().getReference("Reports").push();
        reportsRef.setValue(new Report(nameStreet, time, height, null)).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Temp.text= "تم إرسال البلاغ بنجاح";
                startActivity(new Intent(this, DoneActivity.class));
                finish();
            } else {
                dismissProgressDialog();
            }
        });
    }

}
