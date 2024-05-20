package com.mnashat_dev.touqa.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.mnashat_dev.touqa.R;
import com.mnashat_dev.touqa.base.BaseActivity;
import com.mnashat_dev.touqa.databinding.ActivityDirectionsBinding;
import com.mnashat_dev.touqa.ui.DoneActivity;
import com.mnashat_dev.touqa.util.Temp;

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