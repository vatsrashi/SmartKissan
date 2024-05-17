package com.example.smartkissan;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddVehicle extends AppCompatActivity {

    String[] items = {"Tractor","Combine Harvester","Truck","Farm truck"};

    ArrayAdapter<String> adapterItems ;
    AutoCompleteTextView autoCompleteTextView;
    private FirebaseFirestore db;

    EditText model,owner,regn,other;

    Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        autoCompleteTextView = findViewById(R.id.filled_exposed_dropdown);
        confirm = findViewById(R.id.button7);
        adapterItems = new ArrayAdapter<String>(this,R.layout.list_items,items);

        autoCompleteTextView.setAdapter(adapterItems);
        db = FirebaseFirestore.getInstance();

        model = findViewById(R.id.editTextDate);
        owner = findViewById(R.id.editTextTime2);
        regn = findViewById(R.id.otherinfo);
        other = findViewById(R.id.otherinfo100);


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String putId = generateBookingId();
                // Create a new user with a first and last name
                Map<String, Object> user = new HashMap<>();
                user.put("Vehicle_id",putId);
                user.put("Vehicle", autoCompleteTextView.getEditableText().toString());
                user.put("model", model.getText().toString());
                user.put("owner", owner.getText().toString());
                user.put("regn" , regn.getText().toString());
                user.put("other" , other.getText().toString());

// Add a new document with a generated ID
                db.collection("Vehicle")
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


                Intent added = new Intent(AddVehicle.this, VehicleAdded.class);
                startActivity(added);
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