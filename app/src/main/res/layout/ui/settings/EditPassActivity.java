package com.mnashat_dev.touqa.ui.settings;

import android.os.Bundle;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mnashat_dev.touqa.R;
import com.mnashat_dev.touqa.base.BaseActivity;
import com.mnashat_dev.touqa.databinding.ActivityEditPassBinding;

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
