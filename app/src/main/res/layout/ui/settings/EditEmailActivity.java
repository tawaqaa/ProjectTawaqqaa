package com.mnashat_dev.touqa.ui.settings;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mnashat_dev.touqa.R;
import com.mnashat_dev.touqa.base.BaseActivity;
import com.mnashat_dev.touqa.databinding.ActivityEditEmailBinding;

public class EditEmailActivity extends BaseActivity {

    private ActivityEditEmailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_email);

        binding.save.setOnClickListener(v -> {
            String email = binding.edit.getText().toString().trim();

//            if(isValidEmail(email)){
//                updateItem("email",email);
//            }
        });

    }



    private void updateEmail(String key , String newEmail) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user.updateEmail(newEmail)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Email update successful
                                Toast.makeText(EditEmailActivity.this, "Email updated successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Exception e = task.getException();
                                Toast.makeText(EditEmailActivity.this, "Failed to update email: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(EditEmailActivity.this, "User is not signed in", Toast.LENGTH_SHORT).show();
        }
    }


}