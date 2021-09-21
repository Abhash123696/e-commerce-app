package com.example.mymall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

public class RegisterActivity extends AppCompatActivity {

    FrameLayout RegisterframeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        RegisterframeLayout = findViewById(R.id.RegisterframeLayout);
        setFragment(new SignInFragment());

    }

    // when register activity called , SignIn  fragment is automatically called

    private void setFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(RegisterframeLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}