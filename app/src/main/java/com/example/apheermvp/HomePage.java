package com.example.apheermvp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePage extends AppCompatActivity {
    private TextView displayUserNameWelcome;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        displayUserNameWelcome = findViewById(R.id.home_page_welcome_back_text);
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        // Capture the layout's TextView and set the string as its text
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getInstance().getCurrentUser();
        if(currentUser != null){
            updateUI(currentUser);
        }
    }

    public void updateUI(FirebaseUser user){
        user = mAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        displayUserNameWelcome.setText("Welcome back " +email);
    }
}
