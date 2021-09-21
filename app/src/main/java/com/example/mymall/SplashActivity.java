package com.example.mymall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    ImageView imgView;
    private static int SPLASH_SCREEN_TIME_OUT = 3000;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Now we use Window manager it cover entire screen .

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //The above  method is used so that the  splash activity
        //can cover the entire screen.

        setContentView(R.layout.activity_splash);


        firebaseAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent register = new Intent(SplashActivity.this, RegisterActivity.class);
                startActivity(register);
                finish();          // the splash activity will get finished.
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser == null)  // user chainah vne
        {
            Intent registIntent = new Intent(SplashActivity.this, RegisterActivity.class);
            startActivity(registIntent);
            finish();
        } else {
            Intent mainActivity = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(mainActivity);
            finish();
        }

    }
}