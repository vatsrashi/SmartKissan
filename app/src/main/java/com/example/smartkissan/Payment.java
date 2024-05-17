package com.example.smartkissan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Payment extends AppCompatActivity {

    Button order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        order = findViewById(R.id.order);

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent success = new Intent(Payment.this, OrderSuccess.class);
                startActivity(success);
            }
        });
    }
}