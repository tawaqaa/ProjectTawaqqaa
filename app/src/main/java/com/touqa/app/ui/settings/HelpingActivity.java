package com.touqa.app.ui.settings;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.touqa.app.DoneActivity;
import com.touqa.app.R;
import com.touqa.app.base.BaseActivity;
import com.touqa.app.databinding.ActivityHelpingBinding;
import com.touqa.app.model.Problem;
import com.touqa.app.util.Temp;

public class HelpingActivity extends AppCompatActivity {
private ActivityHelpingBinding binding;
    private Dialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_helping);
        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.editNameProb.getText().toString().trim();
                String desc = binding.editDescProb.getText().toString().trim();

                if(isValidNameProb(name) && isValidDescrProb(desc) ){
                    addNewProblem(name , desc);
                }
            }
        });
        progressDialog = new Dialog(this);
    }

    private void addNewProblem(String name, String desc) {
        String idUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("problems").push();
        usersRef.setValue(new Problem(name, desc, idUser)).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Temp.text = "تم ارسال المشكلة بنجاح" ;
                startActivity(new Intent(this, DoneActivity.class));
                dismissProgressDialog();
                finish();
            } else {
                dismissProgressDialog();
            }
        });
    }

    private boolean isValidNameProb(String name) {
        if (name.isEmpty()) {
            binding.editNameProb.setError("يرجى إدخال عنوان المشكلة");
            return false;
        } else {
            binding.editNameProb.setError(null);
            return true;
        }
    }

    private boolean isValidDescrProb(String desc) {
        if (desc.isEmpty()) {
            binding.editDescProb.setError("يرجى إدخال وصف المشكلة");
            return false;
        } else {
            binding.editDescProb.setError(null);
            return true;
        }
    }

    private void dismissProgressDialog() {
        progressDialog.dismiss();
    }
    private void showProgressDialog() {
        if (progressDialog.getOwnerActivity() == null) {
            progressDialog = new Dialog(this);
        }
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(R.layout.dialog_progress);
        progressDialog.setCanceledOnTouchOutside(false);
        Window window = progressDialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.show();
    }

}