package com.example.mymall;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends Fragment {

    private EditText email;
    private Button forgotPasswordButton;
    private TextView goBack;
    private FirebaseAuth firebaseAuth;


    public ForgotPassword() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_forgot_password,container,false);

        email = view.findViewById(R.id.forgotPasswordEmail);
        forgotPasswordButton = view.findViewById(R.id.resetPasswordButton);
        goBack = view.findViewById(R.id.tvForgotPasswordGoBack);


        firebaseAuth = FirebaseAuth.getInstance();


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });   // email text is empty or not


        // Working on goBack TextView

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slideout_from_right);
                SignInFragment signInFragment = new SignInFragment();
                fragmentTransaction.add(R.id.RegisterframeLayout, signInFragment);
                fragmentTransaction.commit();
            }
        });


        // working on resetPasswordButton

        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // invisible button

                forgotPasswordButton.setEnabled(false);
                forgotPasswordButton.setTextColor(Color.argb(50,255,255,255));

                firebaseAuth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(getActivity(), "email sent sucessfully", Toast.LENGTH_SHORT).show();

                        }else {
                            String error = task.getException().toString();
                            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                        }

                            forgotPasswordButton.setEnabled(true);
                            forgotPasswordButton.setTextColor(Color.rgb(255,255,255));
                    }
                });
            }
        });

    }

    private void checkInput() {

        if(!TextUtils.isEmpty(email.getText().toString())){
            forgotPasswordButton.setEnabled(true);
            forgotPasswordButton.setTextColor(Color.rgb(255,255,255));

        }else{

            forgotPasswordButton.setEnabled(false);
            forgotPasswordButton.setTextColor(Color.argb(0,255,255,255));
        }

    }
}