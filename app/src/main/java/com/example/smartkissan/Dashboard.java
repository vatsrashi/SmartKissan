package com.example.smartkissan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Dashboard extends AppCompatActivity {

    private ImageButton backButton;
    private TextView titleTextView;
    private FloatingActionButton addCropButton;


    private CardView contributeCardView;
    private CardView practiceCardView;
    private CardView learnCardView;
    private CardView transportCardView;

    private CardView helpCardView;
    private CardView settingsCardView;

    private LinearLayout news,bookings,profile;

    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize UI elements
        backButton = findViewById(R.id.backB);
        titleTextView = findViewById(R.id.textView);
        addCropButton = findViewById(R.id.floatingActionButton);
        news = findViewById(R.id.news);
        bookings = findViewById(R.id.booking);
        profile = findViewById(R.id.profile);

        auth = FirebaseAuth.getInstance();

        user = auth.getCurrentUser();
        if(user==null){
            Intent intent = new Intent(getApplicationContext(), SignIn.class);
            startActivity(intent);
            finish();

        }


        contributeCardView = findViewById(R.id.contributeCard);
        practiceCardView = findViewById(R.id.practiceCard);
        learnCardView = findViewById(R.id.learnCard);
        transportCardView = findViewById(R.id.transportCard);

        helpCardView = findViewById(R.id.helpCard);
        settingsCardView = findViewById(R.id.settingsCard);

//        settingsCardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent setting = new Intent(Dashboard.this, SettingsActivity.class);
//                startActivity(setting);
//            }
//        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(Dashboard.this, Profile.class);
                startActivity(profile);
            }
        });

        bookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent my = new Intent(Dashboard.this, BookingActivity.class);
                startActivity(my);
            }
        });

        // Set click listeners
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), SignIn.class);
                startActivity(intent);
                finish();
            }
        });

        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent news = new Intent(Dashboard.this,News.class);
                startActivity(news);
            }
        });



        practiceCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logout = new Intent(Dashboard.this, Weather.class);
                startActivity(logout);
            }
        });

        contributeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tractor = new Intent(Dashboard.this, BookTractor.class);
                startActivity(tractor);
            }
        });

        transportCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent transport = new Intent(Dashboard.this, BookTransport.class);
                startActivity(transport);
            }
        });

        addCropButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addCropIntent = new Intent(Dashboard.this, Select.class);
                startActivity(addCropIntent);
            }
        });

        learnCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Crop = new Intent(Dashboard.this, CropManagementActivity.class);
                startActivity(Crop);
            }
        });

        helpCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent transport = new Intent(Dashboard.this, BuySeeds.class);
                startActivity(transport);
            }
        });

        // You can add more functionality as needed for each button and card view.
        // For example, you can add onClickListeners to smartKissanButton, editProfileButton, etc.
        // Similarly, set click listeners for contributeCardView, practiceCardView, etc.
    }
}
