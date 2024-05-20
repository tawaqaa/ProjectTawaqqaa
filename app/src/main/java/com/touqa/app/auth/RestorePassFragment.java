package com.touqa.app.auth;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.touqa.app.R;
import com.touqa.app.databinding.FragmentRestorePassBinding;

public class RestorePassFragment extends Fragment {
    private FragmentRestorePassBinding binding;
    private Dialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRestorePassBinding.inflate(inflater, container, false);
        progressDialog = new Dialog(requireContext());
        binding.buttonSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.editTextEmail.getText().toString().trim();

                if(isValidEmail(email)){
                    sendPasswordResetEmail(email);
                }
            }
        });

        return binding.getRoot();
    }

    private void sendPasswordResetEmail(String email) {
        showProgressDialog();
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        Query query = usersRef.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dismissProgressDialog();
                if (dataSnapshot.exists()) {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(requireActivity(), "تم إرسال رابط الى بريدك اﻹلكترونى بنجاح", Toast.LENGTH_SHORT).show();
                                    requireActivity().onBackPressed();
                                } else {
                                    Toast.makeText(requireActivity(), "فشل إرسال الرابط: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(requireActivity(), "لم يتم العثور على مستخدم بهذا البريد الإلكتروني", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                dismissProgressDialog();
                Toast.makeText(requireActivity(), "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean isValidEmail(String email) {
        if (email.isEmpty()) {
            binding.editTextEmail.setError("يرجى إدخال البريد اﻹلكترونى");
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editTextEmail.setError("يرجى إدخال بريد إلكترونى صالح");
            return false;
        } else {
            binding.editTextEmail.setError(null);
            return true;
        }
    }

    private void showProgressDialog() {
        if (progressDialog.getOwnerActivity() == null) {
            progressDialog = new Dialog(requireContext());
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

    private void dismissProgressDialog() {
        progressDialog.dismiss();
    }
}
