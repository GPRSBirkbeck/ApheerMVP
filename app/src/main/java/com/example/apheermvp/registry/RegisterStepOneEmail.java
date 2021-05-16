package com.example.apheermvp.registry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apheermvp.R;
import com.example.apheermvp.signIn.Sign_In_Activity;
import com.google.firebase.auth.FirebaseUser;

public class RegisterStepOneEmail extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.apheermvp.EXTRA_MESSAGE";
    private static final String TAG = "MainActivity";
    //From built in auth tutorial: build an authenticcaiton object

    //UI references
    private EditText mEmail, mPassword;
    private Button registerButton, signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step_one_email);//get an instance of that firebase auth object

        mEmail = (EditText) findViewById(R.id.email_input);
        mPassword = (EditText) findViewById(R.id.password_input);
        registerButton = (Button) findViewById(R.id.register_step_two_button);
        signInButton = (Button) findViewById(R.id.sign_in_activity_1);

        //register method for register button
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();

                if(!email.equals("")){
                    goToStepTwo();

                }
                else if (email.equals("")){
                    toastMessage("you didn't enter your email");
                }
            }
        });
    }

    public void updateUI(FirebaseUser currentUser){
        //displayUserNameWelcome.setVisibility(View.VISIBLE);
        //displayUserNameWelcome.setText("Welcome back " + currentUser.getDisplayName());
    }

    public void toastMessage(String toastString){
        Toast.makeText(this, toastString, Toast.LENGTH_SHORT).show();
    }

    public void openLogin(View view) {
        Intent intent = new Intent(this, Sign_In_Activity.class);
        startActivity(intent);
    }

    public void goToStepTwo() {
        Intent intent = new Intent(this, RegisterStepTwoPassword.class);
        String email = mEmail.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, email);
        startActivity(intent);
    }

}