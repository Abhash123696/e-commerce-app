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

public class SignInFragment extends Fragment {

    private EditText email;
    private EditText password;

    private Button signInButton;
    private TextView forgotPassword;
    private ImageButton closeSignInButton;

    private ProgressBar singInProgressBar;

    private FirebaseAuth firebaseAuth;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";



    public SignInFragment() {
        // Required empty public constructor
    }


    TextView dontHaveAnAccount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        dontHaveAnAccount = view.findViewById(R.id.tv_dont_have_an_account);

        // The below code for sign in user code

        email = view.findViewById(R.id.sign_in_email);
        password = view.findViewById(R.id.sign_in_password);
        signInButton = view.findViewById(R.id.sign_in_btn);
        singInProgressBar = view.findViewById(R.id.sign_in_progressBar);
        forgotPassword = view.findViewById(R.id.sign_in_forgot_password);
        closeSignInButton = view.findViewById(R.id.sign_in_close_btn);


        firebaseAuth = FirebaseAuth.getInstance();




        return view;
    }


    // *********   In below method  read be carefully

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dontHaveAnAccount.setOnClickListener(new View.OnClickListener() {    // If confuse than see the lecture of MdJamal playlist of fragment
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();  // getAcivity means it outside the form . It loads the fragment
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slideout_from_left);
                SignUpFragment signUpFragment = new SignUpFragment();
                fragmentTransaction.add(R.id.RegisterframeLayout, signUpFragment);
                fragmentTransaction.commit();
            }
        });

        // for check input field is empty or not

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
        });

        password.addTextChangedListener(new TextWatcher() {
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
        });


        // sign in button

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEmailAndPassword();
            }
        });


        closeSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(getActivity(),MainActivity.class);
                startActivity(mainActivity);
            }
        });

        // ********************  For forgot Password


        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ForgotPassword forgotPassword = new ForgotPassword();
                fragmentTransaction.add(R.id.RegisterframeLayout,forgotPassword);
                fragmentTransaction.commit();
            }
        });



    }

    private void checkEmailAndPassword() {
        if(email.getText().toString().matches(emailPattern)){
            if(password.length() >= 8){

                singInProgressBar.setVisibility(View.VISIBLE);    // progress bar show

                signInButton.setEnabled(false);   //btn disabled
                signInButton.setTextColor(Color.argb(50,255,255,255));

                firebaseAuth.signInWithEmailAndPassword(email.getText().toString() , password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            Intent mainActivity = new Intent(getActivity(), MainActivity.class);
                            startActivity(mainActivity);
                            getActivity().finish();
                            Toast.makeText(getActivity(), "you are logged In", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            signInButton.setEnabled(true);
                            signInButton.setTextColor(Color.rgb(255,255,255));

                            String error = task.getException().toString();
                            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }else {

                Toast.makeText(getActivity(), "Incorrect email or password!", Toast.LENGTH_SHORT).show();
            }

        }else {
                Toast.makeText(getActivity(), "Incorrect email or password!", Toast.LENGTH_SHORT).show();
        }

    }

    // for check email and password

    private void checkInput() {
        if(!TextUtils.isEmpty(email.getText().toString())){
            if(!TextUtils.isEmpty(password.getText().toString())){

                signInButton.setEnabled(true);
                signInButton.setTextColor(Color.rgb(255,255,255));
            }else {
                signInButton.setEnabled(false);
                signInButton.setTextColor(Color.argb(50,255,255,255));
            }

        }else {
            signInButton.setEnabled(false);
            signInButton.setTextColor(Color.argb(50,255,255,255));

        }

    }
}