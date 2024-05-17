package com.example.smartkissan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private TextView totalPriceTextView;

    Button proc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Initialize Toolbar
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recycler_view_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize TextView for total price
        totalPriceTextView = findViewById(R.id.text_total_price);

        // Create sample cart items (replace with actual cart data)
        List<CartItem> cartItemList = new ArrayList<>();
        cartItemList.add(new CartItem("Tomato Seeds", 1450, 2));

        // Calculate total price
        double totalPrice = calculateTotalPrice(cartItemList);

        // Set total price in the TextView
        totalPriceTextView.setText("Total: Rs." + totalPrice);

        // Initialize and set the adapter
        cartAdapter = new CartAdapter(cartItemList);
        recyclerView.setAdapter(cartAdapter);

        proc = findViewById(R.id.proc);

        proc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent proceed = new Intent(Cart.this, Payment.class);
                startActivity(proceed);
            }
        });
    }

    // Method to calculate total price from cart items
    private double calculateTotalPrice(List<CartItem> cartItems) {
        double totalPrice = 0.0;
        for (CartItem item : cartItems) {
            totalPrice += item.getPrice() * item.getQuantity();
        }
        return totalPrice;
    }
}
