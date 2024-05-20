package com.touqa.app.ui.settings;

import android.os.Bundle;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.touqa.app.DoneActivity;
import com.touqa.app.R;
import com.touqa.app.base.BaseActivity;
import com.touqa.app.databinding.ActivityEditPassBinding;

public class EditPassActivity extends BaseActivity {

    private ActivityEditPassBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_pass);

        binding.save.setOnClickListener(v -> {
                    String oldPass = binding.oldPass.getText().toString().trim();
                    String newPass = binding.newPass.getText().toString().trim();
                    String reEnterPass = binding.reEnterPass.getText().toString().trim();
                    if (isValidPass(oldPass) && isValidPass(newPass) && isPasswordMatch(newPass, reEnterPass)) {
                        updatePass(oldPass, newPass);
                    }
                }
        );
    }


    private void updatePass(String oldPass, String newPass) {

        showProgressDialog();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldPass);
            user.reauthenticate(credential)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            user.updatePassword(newPass)
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            Toast.makeText(EditPassActivity.this, "تم تحديث كلمة المرور بنجاح", Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Toast.makeText(EditPassActivity.this, "فشل في تحديث كلمة المرور", Toast.LENGTH_SHORT).show();
                                        }
                                        dismissProgressDialog();

                                    });
                        } else {
                            dismissProgressDialog();
                            Toast.makeText(EditPassActivity.this, "فشل في التحقق من كلمة المرور القديمة", Toast.LENGTH_SHORT).show();
                        }
                    });
            dismissProgressDialog();

        }
    }
}
