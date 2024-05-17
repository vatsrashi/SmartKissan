package com.example.smartkissan;
import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
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

public class BookTransport extends AppCompatActivity {

    String[] items = {"Wheat","Rice","Mustard","Potato"};
    String[] items1 = {"Land1","Land2","Land3"};
    String[] items2 = {"Tons","KG"};
    ArrayAdapter<String> adapterItems, adapterItems1, adapterItems2;
    AutoCompleteTextView autoCompleteTextView,autoCompleteTextView1,autoCompleteTextView2;

    Button btnDatePicker, btnTimePicker , confirm,addLocationBtn;
    EditText txtDate, txtTime , quant;
    TextView dest;
    ImageView back;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private FirebaseFirestore db;
    private static final int REQUEST_CODE_MAP = 101; // Arbitrary request code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_transport);

        btnDatePicker=findViewById(R.id.button2);
        btnTimePicker=findViewById(R.id.button3);
        txtDate=findViewById(R.id.editTextDate);
        txtTime=findViewById(R.id.editTextTime2);
        dest = findViewById(R.id.savedAddressTextView1);
        back = findViewById(R.id.back);
        quant = findViewById(R.id.quant);

        db = FirebaseFirestore.getInstance();


        autoCompleteTextView = findViewById(R.id.filled_exposed_dropdown);
        adapterItems = new ArrayAdapter<String>(this,R.layout.list_items,items);

        autoCompleteTextView.setAdapter(adapterItems);

        autoCompleteTextView1 = findViewById(R.id.filled_exposed_dropdown1);
        adapterItems1 = new ArrayAdapter<String>(this,R.layout.list_items,items1);

        autoCompleteTextView1.setAdapter(adapterItems1);

        autoCompleteTextView2 = findViewById(R.id.filled_exposed_dropdown3);
        adapterItems2 = new ArrayAdapter<String>(this,R.layout.list_items,items2);

        autoCompleteTextView2.setAdapter(adapterItems2);

        confirm = findViewById(R.id.button7);

        addLocationBtn = findViewById(R.id.btnAddLocation);



        // Storing data into SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);

        // Creating an Editor object to edit(write to the file)
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        // Storing the key and its value as the data fetched from edittext
        myEdit.putString("date", txtDate.getText().toString());
        myEdit.putString("time", txtTime.getText().toString());
        myEdit.putString("dest", dest.getText().toString());
        myEdit.putString("crop", autoCompleteTextView.getText().toString());
        myEdit.putString("land", autoCompleteTextView1.getText().toString());
        myEdit.putString("quant", quant.getText().toString()+" "+autoCompleteTextView2.getText().toString());



        // Once the changes have been made, we need to commit to apply those changes made,
        // otherwise, it will throw an error
        myEdit.commit();






        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(BookTransport.this,
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
                TimePickerDialog timePickerDialog = new TimePickerDialog(BookTransport.this,
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

        addLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch TransportMap activity to select location
                Intent intent = new Intent(BookTransport.this, TransportMap.class);
                startActivityForResult(intent, REQUEST_CODE_MAP);
            }
        });


        confirm.setOnClickListener(new View.OnClickListener() {

            String putId = generateBookingId();
            String putQuantity = quant.getText().toString()+ autoCompleteTextView2.getEditableText().toString();

            @Override
            public void onClick(View v) {
                Map<String, Object> user = new HashMap<>();
                user.put("Booking_id",putId);
                user.put("date", txtDate.getText().toString());
                user.put("time", txtTime.getText().toString());
                user.put("dest", dest.getText().toString());
                user.put("crop", autoCompleteTextView.getText().toString());
                user.put("land", autoCompleteTextView1.getText().toString());
                user.put("quant",quant.getText().toString()+" "+autoCompleteTextView2.getEditableText().toString());

// Add a new document with a generated ID
                db.collection("Transport")
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
                Intent transport = new Intent(BookTransport.this, TransportConfirmed.class);
                startActivity(transport);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(BookTransport.this, Dashboard.class);
                startActivity(back);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_MAP && resultCode == RESULT_OK) {
            // Get the selected location from TransportMap activity
            String selectedLocation = data.getStringExtra("selected_location");
            // Display the selected location
            // You can set it to an EditText or any other view in your layout
            dest.setText(selectedLocation);
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
