package com.example.smartkissan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Welcome extends AppCompatActivity {

    FirebaseAuth mAuth;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mAuth = FirebaseAuth.getInstance();

        Button nextButton = findViewById(R.id.welcome_next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigate to the next screen (e.g., User Registration and Login)
                Intent intent = new Intent(Welcome.this, SignIn.class);
                startActivity(intent);
                finish(); // Finish the current WelcomeActivity to prevent going back.
            }
        });
    }
}