package com.example.smartkissan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VehicleAdded extends AppCompatActivity {

    Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_added);

        home = findViewById(R.id.back_home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(VehicleAdded.this, Dashboard.class);
                startActivity(home);
            }
        });
    }
}