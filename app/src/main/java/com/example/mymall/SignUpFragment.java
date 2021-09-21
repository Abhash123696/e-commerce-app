package com.example.mymall;

import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class SignUpFragment extends Fragment {


    public SignUpFragment() {
        // Required empty public constructor
    }


    private TextView alreadyHaveAnAccount;

    //************   The below field is used to Registration field

    private EditText email;
    private EditText fullName;
    private EditText password;
    private EditText confirmPassword;
    private ImageButton closeSignUpButton;
    private ProgressBar progressBar;
    private Button signUpBtn;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        // for taking reference of text view , we can't directly use findViewByIb ,we use first View because we inside the fragment can't directly use.

        alreadyHaveAnAccount = view.findViewById(R.id.tv_already_have_an_account);

        // creating reference of field of Registration

        email = view.findViewById(R.id.sign_up_email);
        fullName = view.findViewById(R.id.sign_up_full_name);
        password = view.findViewById(R.id.sign_up_password);
        confirmPassword = view.findViewById(R.id.sign_up_confirm_password);
        progressBar = view.findViewById(R.id.sign_up_progressBar);
        signUpBtn = view.findViewById(R.id.sign_up_btn);
        closeSignUpButton = view.findViewById(R.id.sign_up_close_btn);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        return view;

    }

    //     *************  important method

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // **************   For fragment

        alreadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
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


        // for check field is empty or not //  if confuse than see ecommerce playlist video number  5

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // not use
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                checkInput();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // not use
            }
        });

        fullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // not use
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                checkInput();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // not use
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // not use
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                checkInput();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // not use
            }
        });

        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // not use
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                checkInput();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // not use
            }
        });


        // ***********************  When user click signUpBtn ,

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEmailAndPassword();
            }
        });



        closeSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(getActivity(),MainActivity.class);
                startActivity(mainActivity);
            }
        });


    }

    private void checkEmailAndPassword() {

        if (email.getText().toString().matches(emailPattern)) {
            if (password.getText().toString().equals(confirmPassword.getText().toString())) {

                progressBar.setVisibility(View.VISIBLE);

                // the below code is used for when user submit the data ,the button is disable because user can't click multiple time

                signUpBtn.setEnabled(false);
                signUpBtn.setTextColor(Color.argb(50, 255, 255, 255));

                // sending data from firebase

                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {

                                    Map<Object, String> userData = new HashMap<>();
                                    userData.put("fullname", fullName.getText().toString());

                                    firebaseFirestore.collection("USERS").add(userData)
                                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                                    if (task.isSuccessful()) {
                                                        Intent mainActivity = new Intent(getActivity(), MainActivity.class);
                                                        startActivity(mainActivity);
                                                        getActivity().finish();

                                                        Toast.makeText(getActivity(), "Your account is created", Toast.LENGTH_SHORT).show();


                                                    } else {

                                                        signUpBtn.setEnabled(true);   // if all valid then show button
                                                        signUpBtn.setTextColor(Color.rgb(255, 255, 255));

                                                        progressBar.setVisibility(View.INVISIBLE);
                                                        String error = task.getException().getMessage();
                                                        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();

                                                    }
                                                }
                                            });


                                } else {
                                    signUpBtn.setEnabled(true);   // if all valid then show button
                                    signUpBtn.setTextColor(Color.rgb(255, 255, 255));

                                    progressBar.setVisibility(View.INVISIBLE);
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

            } else {
                confirmPassword.setError("Password doesn't matched!");

            }

        } else {
            email.setError("Invalid Email");
        }

    }

    // ************** User validation Method


    public void checkInput() {
        if (!TextUtils.isEmpty(email.toString())) {
            if (!TextUtils.isEmpty(fullName.getText().toString())) {
                if (!TextUtils.isEmpty(password.getText().toString()) && password.length() >= 8) {
                    if (!TextUtils.isEmpty(confirmPassword.getText().toString())) {

                        signUpBtn.setEnabled(true);   // if all valid then show button
                        signUpBtn.setTextColor(Color.rgb(255, 255, 255));
                    } else {
                        signUpBtn.setEnabled(false);
                        signUpBtn.setTextColor(Color.argb(50, 255, 255, 255));

                    }

                } else {
                    signUpBtn.setEnabled(false);
                    signUpBtn.setTextColor(Color.argb(50, 255, 255, 255));

                }

            } else {
                signUpBtn.setEnabled(false);
                signUpBtn.setTextColor(Color.argb(50, 255, 255, 255));

            }

        } else {
            signUpBtn.setEnabled(false);
            signUpBtn.setTextColor(Color.argb(50, 255, 255, 255));

        }
    }
}