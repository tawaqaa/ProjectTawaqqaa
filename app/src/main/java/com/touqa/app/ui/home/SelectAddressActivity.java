package com.touqa.app.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;
import com.touqa.app.CustomPagerAdapter;
import com.touqa.app.DoneActivity;
import com.touqa.app.R;
import com.touqa.app.base.BaseActivity;
import com.touqa.app.databinding.ActivitySafetyBinding;
import com.touqa.app.databinding.ActivitySelectAddressBinding;
import com.touqa.app.model.PageItem;
import com.touqa.app.util.Temp;

public class SelectAddressActivity  extends BaseActivity {

    private ActivitySelectAddressBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_address);

        binding.btnSend.setOnClickListener(v -> {
            Temp.text= "تم إرسال البلاغ بنجاح";
            startActivity(new Intent(this, DoneActivity.class));
            finish();
        });

    }
}