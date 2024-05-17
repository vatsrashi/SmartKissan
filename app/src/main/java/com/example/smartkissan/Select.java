package com.example.smartkissan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Select extends AppCompatActivity {

    CardView crop,vehicle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        crop = findViewById(R.id.helpCard);
        vehicle = findViewById(R.id.settingsCard);

        crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Crop = new Intent(Select.this, AddCrop.class);
                startActivity(Crop);
            }
        });

        vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Crop = new Intent(Select.this, AddVehicle.class);
                startActivity(Crop);
            }
        });
    }
}