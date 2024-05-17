package com.example.smartkissan;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BookTractor extends AppCompatActivity {

    String[] items = {"Harvesting","Grinding","Seeding","Ploughing"};
    String[] items1 = {"Land1","Land2","Land3"};
    ArrayAdapter<String> adapterItems, adapterItems1;
    AutoCompleteTextView autoCompleteTextView,autoCompleteTextView1;

    Button btnDatePicker, btnTimePicker , confirm;
    EditText txtDate, txtTime, other;
    ImageView back;
    private int mYear, mMonth, mDay, mHour, mMinute;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_tractor);

        btnDatePicker=findViewById(R.id.button2);
        btnTimePicker=findViewById(R.id.button3);
        txtDate=findViewById(R.id.editTextDate);
        txtTime=findViewById(R.id.editTextTime2);
        back = findViewById(R.id.back);
        other = findViewById(R.id.otherinfo);

        autoCompleteTextView = findViewById(R.id.filled_exposed_dropdown);
        adapterItems = new ArrayAdapter<String>(this,R.layout.list_items,items);

        autoCompleteTextView.setAdapter(adapterItems);

        autoCompleteTextView1 = findViewById(R.id.filled_exposed_dropdown1);
        adapterItems1 = new ArrayAdapter<String>(this,R.layout.list_items,items1);

        autoCompleteTextView1.setAdapter(adapterItems1);

        confirm = findViewById(R.id.button7);

        db = FirebaseFirestore.getInstance();



        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(BookTractor.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);


                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();

            }
        });

        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(BookTractor.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                txtTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String putTime = txtTime.getText().toString();
                String putDate = txtDate.getText().toString();
                String putAct = autoCompleteTextView.getEditableText().toString();
                String putLand = autoCompleteTextView1.getEditableText().toString();
                String putOther = other.getText().toString();
                String putId = generateBookingId();
                // Create a new user with a first and last name
                Map<String, Object> user = new HashMap<>();
                user.put("Booking_id",putId);
                user.put("Date", putDate);
                user.put("Time", putTime);
                user.put("Activity", putAct);
                user.put("Land" , putLand);
                user.put("Other" , putOther);

// Add a new document with a generated ID
                db.collection("Tractor")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(), "Save SUCCESS.",
                                        Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Save fail.",
                                        Toast.LENGTH_SHORT).show();
                                Log.w(TAG, "Error adding document", e);
                            }
                        });
                Intent tractor = new Intent(BookTractor.this, TractorConfirmed.class);
                startActivity(tractor);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(BookTractor.this, Dashboard.class);
                startActivity(back);
            }
        });


    }
    public static String generateBookingId() {
        // Generate a random UUID (Universally Unique Identifier)
        UUID uuid = UUID.randomUUID();

        // Convert UUID to String and remove hyphens
        String bookingId = uuid.toString().replace("-", "");

        // Get current timestamp
        long timestamp = System.currentTimeMillis();

        // Append timestamp to the booking ID
        bookingId += String.valueOf(timestamp);

        // Ensure the booking ID is unique
        // You might want to add additional logic here if required

        return bookingId;
    }
}