package com.touqa.app.ui.settings;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.touqa.app.R;
import com.touqa.app.base.BaseActivity;
import com.touqa.app.databinding.ActivityEditPhoneBinding;

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