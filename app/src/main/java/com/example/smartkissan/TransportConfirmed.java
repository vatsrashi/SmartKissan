package com.example.smartkissan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TransportConfirmed extends AppCompatActivity {


    TextView t1,t2,t3,t4,t5,t6,t7;

    Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_confirmed);

        home = findViewById(R.id.back_home);
        t1 = findViewById(R.id.booking_id_value3);
        t2 = findViewById(R.id.booking_id_value2);
        t3 = findViewById(R.id.booking_id_value5);
        t4 = findViewById(R.id.booking_id_value1);
        t5 = findViewById(R.id.booking_id_value23);
        t6 = findViewById(R.id.booking_id_title22);
        t7 = findViewById(R.id.booking_id_title20);



        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);


        String s1 = sh.getString("date", "");
        String s2 = sh.getString("time", "");
        String s3 = sh.getString("crop", "");
        String s4 = sh.getString("dest", "");
        String s5 = sh.getString("land", "");
        String s6 = sh.getString("quant", "");




      // We can then use the data
//        t1.setText(s2);
//        t2.setText(s1);
//        t3.setText(s5);
//        t4.setText(s4);
//        t5.setText(s6);
//        t6.setText(s3);




        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent transport = new Intent(TransportConfirmed.this, Dashboard.class);
                startActivity(transport);
            }
        });
    }
}