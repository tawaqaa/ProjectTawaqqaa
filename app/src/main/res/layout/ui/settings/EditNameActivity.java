package com.mnashat_dev.touqa.ui.settings;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mnashat_dev.touqa.R;
import com.mnashat_dev.touqa.databinding.ActivityEditNameBinding;
import com.mnashat_dev.touqa.ui.DoneActivity;

public class EditNameActivity extends AppCompatActivity {

    private ActivityEditNameBinding binding;
    private Dialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_name);
        progressDialog = new Dialog(this);

        binding.save.setOnClickListener(v -> {
            String name = binding.editTextName.getText().toString().trim();

            if(isValidName(name)){
                saveUserName(name);
            }
        });
    }

    private void saveUserName(String newUserName) {
        showProgressDialog();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
            userRef.child("name").setValue(newUserName)
                    .addOnSuccessListener(aVoid -> {
                        saveNameLocally(newUserName);
                        Toast.makeText(this, "تم تحديث الاسم بنجاح", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, DoneActivity.class));
                        dismissProgressDialog();
                        finish();
                    })
                    .addOnFailureListener(e -> {
                                Toast.makeText(EditNameActivity.this, "فشل تحديث الاسم", Toast.LENGTH_SHORT).show();
                                dismissProgressDialog();
                            }
                            );
        }
    }


    private boolean isValidName(String name) {
        if (name.isEmpty()) {
            binding.editTextName.setError("يرجى إدخال اسم المستخدم الجديد");
            return false;
        } else {
            binding.editTextName.setError(null);
            return true;
        }
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
    private void saveNameLocally(String name) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.apply();
    }

    private void dismissProgressDialog() {
        progressDialog.dismiss();
    }
}