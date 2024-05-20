package com.touqa.app;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.touqa.app.base.BaseActivity;
import com.touqa.app.databinding.ActivityDoneBinding;
import com.touqa.app.util.Temp;


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