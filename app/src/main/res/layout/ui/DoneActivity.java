package com.mnashat_dev.touqa.ui;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.mnashat_dev.touqa.R;
import com.mnashat_dev.touqa.base.BaseActivity;
import com.mnashat_dev.touqa.databinding.ActivityDoneBinding;
import com.mnashat_dev.touqa.util.Temp;

public class DoneActivity extends BaseActivity {
    private ActivityDoneBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_done);

        if ( Temp.text != null){
            binding.description.setText( Temp.text);
        }
        binding.button.setOnClickListener(v -> DoneActivity.this.onBackPressed());

    }

    @Override
    protected void onStop() {
        super.onStop();
        Temp.text = null;
    }
}