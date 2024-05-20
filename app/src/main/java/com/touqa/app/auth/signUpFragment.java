package com.touqa.app.auth;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.touqa.app.MainActivity;
import com.touqa.app.R;
import com.touqa.app.databinding.FragmentSignUpBinding;
import com.touqa.app.model.User;
public class signUpFragment extends Fragment {


private FragmentSignUpBinding binding;

    private FirebaseAuth mAuth;
    private Dialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);

        mAuth = FirebaseAuth.getInstance();
        binding.login.setOnClickListener(v -> navigateToLogin());
        progressDialog = new Dialog(requireContext());

        binding.buttonSignUp.setOnClickListener(v -> validation());

        return binding.getRoot();

    }

    private void validation() {

//        if (isValidName()&& isValidPhone() && isValidEmail()   && isValidPass() && isPasswordMatch()) {
//            signUpWithEmailAndPassword();
//        }

        if (isValidName()&& isValidPhone() && isValidEmail()) {
            signUpWithEmailAndPassword();
        }

    }
    private void signUpWithEmailAndPassword() {
        showProgressDialog();
        String email = binding.editTextEmail.getText().toString().trim();
        String password = binding.editTextPassword.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            saveUserDataLocally(user.getUid(), binding.editTextName.getText().toString().trim(),
                                    binding.editTextPhone.getText().toString().trim(), email);

                            storeUserDataInFirebase(user.getUid(), binding.editTextName.getText().toString().trim(),
                                    binding.editTextPhone.getText().toString().trim(), email);

                            Toast.makeText(requireContext(), "تم إنشاء حساب جديد بنجاح", Toast.LENGTH_SHORT).show();
                            navigateToMainActivity();
                            dismissProgressDialog();

                        }
                    } else {
                        dismissProgressDialog();
                        Toast.makeText(requireContext(), "فشل إنشاء حساب جديد : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void navigateToMainActivity() {
        startActivity(new Intent(requireActivity(), MainActivity.class));
        requireActivity().finish();
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

    private void storeUserDataInFirebase(String userId, String name, String phone, String email) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
        usersRef.child(userId).setValue(new User(name, phone, email));
    }
    private boolean isValidEmail() {
        String email = binding.editTextEmail.getText().toString().trim();
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

    private boolean isValidPhone() {
        String phone = binding.editTextPhone.getText().toString().trim();
        if (phone.isEmpty()) {
            binding.editTextPhone.setError("يرجى إدخال رقم الجوال");
            return false;
        } else {
            binding.editTextPhone.setError(null);
            return true;
        }
    }

    private boolean isValidName() {
        String name = binding.editTextName.getText().toString().trim();
        if (name.isEmpty()) {
            binding.editTextName.setError("يرجى إدخال اسم المستخدم");
            return false;
        } else {
            binding.editTextName.setError(null);
            return true;
        }
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


    private boolean isPasswordMatch() {
        String password = binding.editTextPassword.getText().toString().trim();
        String repeatPassword = binding.editTextRepeatPassword.getText().toString().trim();
        if (repeatPassword.isEmpty()) {
            binding.editTextRepeatPassword.setError("يرجى إدخال كلمة المرور ");
            return false;
        } else  if (!password.equals(repeatPassword)) {
            binding.editTextRepeatPassword.setError("كلمة المرور غير متطابقة");
            return false;
        } else {
            binding.editTextRepeatPassword.setError(null);
            return true;
        }
    }

    private void navigateToLogin() {
        NavHostFragment navHostFragment =
                (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_auth);
        NavController navController = navHostFragment.getNavController();
        navController.navigate(R.id.action_signUpFragment_to_loginFragment);
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