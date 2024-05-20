package com.touqa.app.auth;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.touqa.app.MainActivity;
import com.touqa.app.R;
import com.touqa.app.databinding.FragmentLoginBinding;
import com.touqa.app.model.User;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private Dialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        progressDialog = new Dialog(requireContext());
        binding.signUp.setOnClickListener(v -> navigateToSignUp());
        binding.forgetPass.setOnClickListener(v -> navigateToRestorePass());

        binding.buttonSignUp.setOnClickListener(v -> {
            String inputEmail = binding.editTextEmail.getText().toString().trim();
            String inputPass = binding.editTextPassword.getText().toString().trim();

            if (isValidEmail() && isValidPass()) {
                if(isEmail(inputEmail)){
                    signInWithEmail(inputEmail,inputPass);
                }else {
                    checkUserExists(inputEmail,inputPass);
                }
            }
//
//            if (isValidEmail()) {
//                if(isEmail(inputEmail)){
//                    Log.e("TAG", " 12 ");
//                    signInWithEmail(inputEmail,inputPass);
//                }else {
//                    checkUserExists(inputEmail,inputPass);
//                }
//            }



        });
        return binding.getRoot();

    }
    private boolean isValidEmail() {
        String email = binding.editTextEmail.getText().toString().trim();
        if (email.isEmpty()) {
            binding.editTextEmail.setError("يرجى إدخال رقم الجوال او البريد اﻹلكترونى");
            return false;
        } else {
            binding.editTextEmail.setError(null);
            return true;
        }
    }
    private boolean isEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    private boolean isValidPass() {
        String password = binding.editTextPassword.getText().toString().trim();
        if (password.isEmpty()) {
            binding.editTextPassword.setError("يرجى إدخال كلمة المرور ");
            return false;
        } else if (password.length() < 12) {
            binding.editTextPassword.setError("يجب أن تكون كلمة المرور على الأقل 12 حرفًا");
            return false;
        } else if (!password.matches(".*[a-z].*")) {
            binding.editTextPassword.setError("يجب أن تحتوي كلمة المرور على حرف صغير واحد على الأقل");
            return false;
        } else if (!password.matches(".*[A-Z].*")) {
            binding.editTextPassword.setError("يجب أن تحتوي كلمة المرور على حرف كبير واحد على الأقل");
            return false;
        } else if (!password.matches(".*\\d.*")) {
            binding.editTextPassword.setError("يجب أن تحتوي كلمة المرور على رقم واحد على الأقل");
            return false;
        } else if (!password.matches(".*[@#$%^&+=].*")) {
            binding.editTextPassword.setError("يجب أن تحتوي كلمة المرور على علامة خاصة واحدة على الأقل");
            return false;
        } else {
            binding.editTextPassword.setError(null);
            return true;
        }
    }
    private void signInWithEmail(String email, String pass) {
        showProgressDialog();
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            retrieveAndStoreUserDataLocally(user);

                        } else {
                            Toast.makeText(requireContext(), "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            dismissProgressDialog();

                        }
                    }
                });
    }

    private void checkUserExists(final String phoneNumber, final String pass) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        Query query = usersRef.orderByChild("phone").equalTo(phoneNumber);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String email = snapshot.child("email").getValue(String.class);
                        if (email != null) {
                            signInWithEmail(email, pass);
                            return;
                        }
                    }
                    Toast.makeText(getContext(), "لم يتم العثور على بريد إلكتروني لهذا المستخدم", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "يرجى التأكد من رقم الجوال ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToSignUp() {
        NavHostFragment navHostFragment =
                (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_auth);
        NavController navController = navHostFragment.getNavController();
        navController.navigate(R.id.action_loginFragment_to_signUpFragment);
    }
    private void navigateToRestorePass() {
        NavHostFragment navHostFragment =
                (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_auth);
        NavController navController = navHostFragment.getNavController();
        navController.navigate(R.id.action_loginFragment_to_restorePassFragment);
    }

   private void navigateToMainActivity() {
       startActivity(new Intent(requireActivity(), MainActivity.class));
       requireActivity().finish();
       Toast.makeText(requireContext(), "مرحبا بك ", Toast.LENGTH_SHORT).show();
    }


    private void retrieveAndStoreUserDataLocally(FirebaseUser user) {
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
            usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        User userData = dataSnapshot.getValue(User.class);
                        if (userData != null) {
                            String name = userData.getName();
                            String phone = userData.getPhone();
                            String email = userData.getEmail();
                            saveUserDataLocally(userId, name, phone, email);
                            navigateToMainActivity();
                            dismissProgressDialog();
                        }
                    } else {
                        Log.e("TAG", "User data not found in database");
                        dismissProgressDialog();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("TAG", "Failed to retrieve user data: " + databaseError.getMessage());
                    dismissProgressDialog();
                }
            });
        }
    }

    private void saveUserDataLocally(String userId, String name, String phone, String email) {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userId", userId);
        editor.putString("name", name);
        editor.putString("phone", phone);
        editor.putString("email", email);
        editor.apply();
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