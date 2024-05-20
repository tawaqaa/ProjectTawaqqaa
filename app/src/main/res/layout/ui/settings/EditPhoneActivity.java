package com.mnashat_dev.touqa.ui.settings;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.mnashat_dev.touqa.R;
import com.mnashat_dev.touqa.base.BaseActivity;
import com.mnashat_dev.touqa.databinding.ActivityEditPhoneBinding;

public class EditPhoneActivity extends BaseActivity {

    private ActivityEditPhoneBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_phone);

        binding.save.setOnClickListener(v -> {
            String string = binding.edit.getText().toString().trim();

            if(isValidPhone(string)){
                updateItem("phone",string);
            }
        });

    }
}