package com.example.apheermvp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_EMAIL = "com.example.apheermvp.EMAIL";
    private static final String TAG = "MainActivity";
    //From built in auth tutorial: build an authenticcaiton object
    private FirebaseAuth mAuth;

    //UI references
    private EditText mEmail, mPassword;
    private Button registerButton, signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get an instance of that firebase auth object
        mAuth = FirebaseAuth.getInstance();

        mEmail = (EditText) findViewById(R.id.email_input);
        mPassword = (EditText) findViewById(R.id.password_input);
        registerButton = (Button) findViewById(R.id.register_button);
        signInButton = (Button) findViewById(R.id.sign_in_activity);

        //register method for register button
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                if(!email.equals("") && !password.equals("")){
                    mAuth.createUserWithEmailAndPassword(email,password);
                    goToHomePage();

                }
                else if (email.equals("")){
                    toastMessage("you didnt enter your email");
                }
                else if(password.equals("")){
                    toastMessage("you didnt enter your password");
                }
                else{
                    toastMessage("you didnt enter an email or a password");
                }
            }
        });
    }

    //When initializing your Activity, check to see if the user is currently signed in.
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getInstance().getCurrentUser();
        if(currentUser != null){
            updateUI(currentUser);
        }
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
    public void goToHomePage() {
        Intent intent = new Intent(this, HomePage.class);
        FirebaseUser currentUser = mAuth.getInstance().getCurrentUser();
        String email = currentUser.getEmail();
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        startActivity(intent);
    }

}