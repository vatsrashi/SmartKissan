package com.example.smartkissan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OrderSuccess extends AppCompatActivity {

    Button back,continueshop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);

        back = findViewById(R.id.backto);
        continueshop = findViewById(R.id.continueshop);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(OrderSuccess.this, Dashboard.class);
                startActivity(back);
            }
        });

        continueshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(OrderSuccess.this, BuySeeds.class);
                startActivity(back);
            }
        });
    }
}