package com.touqa.app.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.touqa.app.DoneActivity;
import com.touqa.app.R;
import com.touqa.app.base.BaseActivity;
import com.touqa.app.databinding.ActivityDirectionsBinding;
import com.touqa.app.util.Temp;


public class DirectionsActivity extends BaseActivity {

    private ActivityDirectionsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_directions);

        binding.btnNext.setOnClickListener(v -> {
                    Temp.text= "لقد تم الوصول لوجهتك بنجاح";
                    startActivity(new Intent(this, DoneActivity.class));
                    finish();
        }


        );

    }}