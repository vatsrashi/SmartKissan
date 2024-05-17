package com.example.smartkissan;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {

    TextInputLayout name,emailid,phone,pass;
    private SharedPreferences sharedPreferences;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.name_signup);
        emailid = findViewById(R.id.email_signup);
        pass = findViewById(R.id.pass_signup);
        phone = findViewById(R.id.phone_signup);
        mAuth = FirebaseAuth.getInstance();

        Button registerButton = findViewById(R.id.signup_btn);

        sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameE = name.getEditText().getText().toString();
                String email = emailid.getEditText().getText().toString();
                String password = pass.getEditText().getText().toString();
                String phoneE = phone.getEditText().getText().toString();

//                if (validateInput(nameE, email, password)) {

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(SignUp.this, "Account created",
                                                Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(getApplicationContext(), SignIn.class);
                                        startActivity(intent);
                                        finish();

                                    } else {
                                        // If sign in fails, display a message to the user.

                                        Toast.makeText(SignUp.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

               // }
            }
        });
    }

    private boolean validateInput(String name, String email, String password) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            showToast("All fields are required.");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Invalid email address. Format: example@example.com");
            return false;
        } else {
            return true;
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}