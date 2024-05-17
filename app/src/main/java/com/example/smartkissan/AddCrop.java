package com.example.smartkissan;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

public class AddCrop extends AppCompatActivity {

    private EditText editTextDate;
    private EditText editTextTime;
    private Button datePickerButton;
    private Button timePickerButton;
    private EditText editTextCropName;
    private EditText editTextSoilType;
    private EditText editTextOtherInfo;
    private Button confirmButton;
    private Button addLocationButton;
    ImageView back;
    private FirebaseFirestore db;

    private TextView savedAddressTextView;

    private static final int MAPS_REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_crop);

        // Initialize UI elements
        editTextDate = findViewById(R.id.editTextDate);
        editTextTime = findViewById(R.id.editTextTime2);
        datePickerButton = findViewById(R.id.button2);
        timePickerButton = findViewById(R.id.button3);
        editTextCropName = findViewById(R.id.editTextText8);
        editTextSoilType = findViewById(R.id.editTextText5);
        editTextOtherInfo = findViewById(R.id.otherinfo);
        confirmButton = findViewById(R.id.button7);
        addLocationButton = findViewById(R.id.addLocationButton);
        savedAddressTextView = findViewById(R.id.savedAddressTextView);
        back = findViewById(R.id.back);

        db = FirebaseFirestore.getInstance();

        // Retrieve saved address from SharedPreferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String savedAddress = sharedPreferences.getString("entered_address", "");
        // Display saved address in the TextView
        savedAddressTextView.setText(savedAddress);

        // Set onClickListener for the date picker button
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        // Set onClickListener for the time picker button
        timePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

        // Set onClickListener for the confirmButton
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get data from EditText fields
                String date = editTextDate.getText().toString();
                String time = editTextTime.getText().toString();
                String cropName = editTextCropName.getText().toString();
                String soilType = editTextSoilType.getText().toString();
                String otherInfo = editTextOtherInfo.getText().toString();

                // Retrieve saved address from TextView
                String address = savedAddressTextView.getText().toString();
                String putId = generateBookingId();

                Map<String, Object> user = new HashMap<>();
                user.put("Crop_id",putId);
                user.put("date", date);
                user.put("time", time);
                user.put("crop", cropName);
                user.put("soil", soilType);
                user.put("land name", otherInfo);
                user.put("address",address);

// Add a new document with a generated ID
                db.collection("Crop")
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


                // Create an Intent to pass data to CropManagementActivity
                Intent intent = new Intent(AddCrop.this, CropManagementActivity.class);
                intent.putExtra("cropName", cropName);
                intent.putExtra("plantingDate", date);
                intent.putExtra("soilType", soilType);
                intent.putExtra("otherInfo", otherInfo);
                intent.putExtra("address", address); // Pass the address value


                // Start CropManagementActivity
                startActivity(intent);

                // Finish the current activity
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(AddCrop.this, Dashboard.class);
                startActivity(back);
            }
        });

        // Set onClickListener for the addLocationButton
        addLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to Maps activity to add location
                startActivityForResult(new Intent(AddCrop.this, Maps.class), MAPS_REQUEST_CODE);
            }
        });
    }

    // Method to show date picker dialog
    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        editTextDate.setText(selectedDate);
                    }
                },
                year, month, day);
        datePickerDialog.show();
    }

    // Method to show time picker dialog
    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String selectedTime = hourOfDay + ":" + minute;
                        editTextTime.setText(selectedTime);
                    }
                },
                hour, minute, false);
        timePickerDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MAPS_REQUEST_CODE && resultCode == RESULT_OK) {
            double latitude = data.getDoubleExtra("latitude", 0.0);
            double longitude = data.getDoubleExtra("longitude", 0.0);
            String address = data.getStringExtra("address");

            // Create an Intent to pass data to CropManagementActivity
            Intent intent = new Intent(AddCrop.this, CropManagementActivity.class);
            intent.putExtra("cropName", editTextCropName.getText().toString());
            intent.putExtra("plantingDate", editTextDate.getText().toString());
            intent.putExtra("soilType", editTextSoilType.getText().toString());
            intent.putExtra("otherInfo", editTextOtherInfo.getText().toString());
            intent.putExtra("address", address);

            // Start CropManagementActivity
            startActivity(intent);

            // Finish the current activity
            finish();
        }
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
