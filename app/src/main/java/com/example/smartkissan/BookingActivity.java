package com.example.smartkissan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class BookingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookingAdapter bookingAdapter;
    private List<Booking> bookingList;

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        // Initialize RecyclerView and bookingList
        recyclerView = findViewById(R.id.recyclerViewBookings);
        back = findViewById(R.id.back);
        bookingList = new ArrayList<>();

        // Add sample bookings (replace with your actual booking data)
        bookingList.add(new Booking("2024-03-12", "10:00 AM", "Tractor", "Harvesting", "Rs.500"));
        bookingList.add(new Booking("2024-03-13", "3:00 PM", "Tractor", "Grinding", "Rs.800"));
        bookingList.add(new Booking("2024-03-14", "11:30 AM", "Transport", "Crops", "Rs.300"));
        bookingList.add(new Booking("2024-03-14", "11:30 AM", "Tractor", "Seeding", "Rs.30"));
        bookingList.add(new Booking("2024-03-14", "11:30 AM", "Transport", "Crops", "Rs.30"));
        bookingList.add(new Booking("2024-03-14", "11:30 AM", "Tractor", "Ploughing", "Rs.30"));
        bookingList.add(new Booking("2024-03-14", "11:30 AM", "Transport", "Crops", "Rs.30"));
        bookingList.add(new Booking("2024-03-14", "11:30 AM", "Tractor", "Seeding", "Rs.30"));
        bookingList.add(new Booking("2024-03-14", "11:30 AM", "Tractor", "Harvesting", "Rs.30"));

        // Set up RecyclerView with BookingAdapter
        bookingAdapter = new BookingAdapter(bookingList);
        recyclerView.setAdapter(bookingAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(BookingActivity.this, Dashboard.class);
                startActivity(back);
            }
        });
    }
}
