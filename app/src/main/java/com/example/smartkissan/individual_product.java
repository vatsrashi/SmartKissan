package com.example.smartkissan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class individual_product extends AppCompatActivity {

    Button buy , addCart;
    ImageView back;

    private int quantity = 1;
    EditText quantityProductPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_product);

        buy = findViewById(R.id.buy_now);
        addCart = findViewById(R.id.add_to_cart);
        back = findViewById(R.id.back);
        quantityProductPage = findViewById(R.id.quantityProductPage);
        quantityProductPage.setText("1");

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logout = new Intent(individual_product.this, Cart.class);
                startActivity(logout);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(individual_product.this, BuySeeds.class);
                startActivity(back);
            }
        });

        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(individual_product.this, "Added to cart successfully", Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void decrement(View view) {
        if (quantity > 1) {
            quantity--;
            quantityProductPage.setText(String.valueOf(quantity));
        }
    }

    public void increment(View view) {
        if (quantity < 500) {
            quantity++;
            quantityProductPage.setText(String.valueOf(quantity));
        } else {
            Toast.makeText(individual_product.this, "Product Count Must be less than 500", Toast.LENGTH_LONG).show();
        }
    }
}