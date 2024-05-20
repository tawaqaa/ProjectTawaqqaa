package com.touqa.app.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.touqa.app.DoneActivity;
import com.touqa.app.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseActivity extends AppCompatActivity {
    private Dialog progressDialog;

    @Override
    protected void onStart() {
        super.onStart();
        progressDialog = new Dialog(this);

    }

    public void dismissProgressDialog() {
        progressDialog.dismiss();
    }
    public void showProgressDialog() {
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

    public boolean isValidEmail(String email) {
        if (email.isEmpty()) {
            Toast.makeText(this, "يرجى إدخال البريد اﻹلكترونى", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "يرجى إدخال بريد إلكترونى صالح", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public void updateItem(String key,String value ) {
        showProgressDialog();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
            userRef.child(key).setValue(value)
                    .addOnSuccessListener(aVoid -> {
                        saveItemLocally(value , key);
                        Toast.makeText(this, "تم تحديث البيانات بنجاح", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, DoneActivity.class));
                        dismissProgressDialog();
                        finish();
                    })
                    .addOnFailureListener(e -> {
                                Toast.makeText(this, "فشل تحديث البيانات", Toast.LENGTH_SHORT).show();
                                dismissProgressDialog();
                            }
                    );
        }
    }

    public void saveItemLocally(String value , String key) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }


    public boolean isValidPhone( String phone) {
        if (phone.isEmpty()) {
            Toast.makeText(this, "يرجى إدخال رقم الجوال", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public boolean isValidName(String name) {
        if (name.isEmpty()) {
            Toast.makeText(this, "يرجى إدخال اسم المستخدم", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public boolean isValidPass(   String password) {
        if (password.isEmpty()) {
            Toast.makeText(this, "يرجى إدخال كلمة المرور", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.length() < 12) {
            Toast.makeText(this, "يجب أن تكون كلمة المرور على الأقل 12 حرفًا", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!password.matches(".*[a-z].*")) {
            Toast.makeText(this, "يجب أن تحتوي كلمة المرور على حرف صغير واحد على الأقل", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!password.matches(".*[A-Z].*")) {
            Toast.makeText(this, "يجب أن تحتوي كلمة المرور على حرف كبير واحد على الأقل", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!password.matches(".*\\d.*")) {
            Toast.makeText(this, "يجب أن تحتوي كلمة المرور على رقم واحد على الأقل", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!password.matches(".*[@#$%^&+=].*")) {
            Toast.makeText(this, "يجب أن تحتوي كلمة المرور على علامة خاصة واحدة على الأقل", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public boolean isPasswordMatch( String password, String repeatPassword) {
        if (repeatPassword.isEmpty()) {
            Toast.makeText(this, "يرجى إدخال كلمة المرور", Toast.LENGTH_SHORT).show();
            return false;
        } else  if (!password.equals(repeatPassword)) {
            Toast.makeText(this, "كلمة المرور غير متطابقة", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
    public  String getCurrentTimeFormatted() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("h.mm a");
        return dateFormat.format(new Date());
    }

}
