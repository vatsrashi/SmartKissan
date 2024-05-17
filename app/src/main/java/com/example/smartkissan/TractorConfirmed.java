package com.example.smartkissan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TractorConfirmed extends AppCompatActivity {

    Button back;
    TextView bookingIdValue, serviceValue, activityValue, landValue, dateValue, timeValue;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tractor_confirmed);

        back = findViewById(R.id.back_home);
        bookingIdValue = findViewById(R.id.booking_id_value);
        serviceValue = findViewById(R.id.booking_id_value20);
        activityValue = findViewById(R.id.booking_id_value1);
        landValue = findViewById(R.id.booking_id_value5);
        dateValue = findViewById(R.id.booking_id_value2);
        timeValue = findViewById(R.id.booking_id_value3);

        sharedPreferences = getSharedPreferences("BookingDetails", MODE_PRIVATE);

        // Retrieve booking details from SharedPreferences
        String bookingId = sharedPreferences.getString("bookingId", "");
        String service = sharedPreferences.getString("service", "");
        String activity = sharedPreferences.getString("activity", "");
        String land = sharedPreferences.getString("land", "");
        String date = sharedPreferences.getString("date", "");
        String time = sharedPreferences.getString("time", "");

        // Display booking details
//        bookingIdValue.setText(bookingId);
//        serviceValue.setText(service);
//        activityValue.setText(activity);
//        landValue.setText(land);
//        dateValue.setText(date);
//        timeValue.setText(time);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tractor = new Intent(TractorConfirmed.this, Dashboard.class);
                startActivity(tractor);
            }
        });
    }
}
