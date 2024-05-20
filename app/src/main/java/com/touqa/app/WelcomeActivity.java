package com.touqa.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.touqa.app.auth.AuthActivity;


public class WelcomeActivity extends AppCompatActivity {

    private static final int SPLASH_TIMEOUT = 2000; // 2 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isLogged();
                }
            }, SPLASH_TIMEOUT);

    }

    private void isLogged() {



        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            finish();
        } else {
            startActivity(new Intent(WelcomeActivity.this, AuthActivity.class));
            finish();
        }

    }
}
