package com.example.smartkissan;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Map;

public class CropManagementActivity extends AppCompatActivity {

    private LinearLayout parentLayout;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_management);

        parentLayout = findViewById(R.id.parentLayout);
        back = findViewById(R.id.back);

        // Retrieve and display existing crop details
        displayExistingCrops();

        // Retrieve data from Intent if a new crop is added
        Intent intent = getIntent();
        String cropName = intent.getStringExtra("cropName");
        String plantingDate = intent.getStringExtra("plantingDate");
        String soilType = intent.getStringExtra("soilType");
        String otherInfo = intent.getStringExtra("otherInfo");
        String address = intent.getStringExtra("address");


        // Add the newly added crop to the view
        if (cropName != null && !cropName.isEmpty()) {
            addCropView(cropName, plantingDate, soilType, otherInfo, address);
            // Save the newly added crop details in SharedPreferences
            saveCropDetails(cropName, plantingDate, soilType, otherInfo, address);

        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(CropManagementActivity.this, Dashboard.class);
                startActivity(back);
            }
        });
    }

    private void displayExistingCrops() {

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);


        String s1 = sh.getString("crop", "");
        String s2 = sh.getString("plant", "");
        String s3 = sh.getString("soil", "");
        String s4 = sh.getString("info", "");
        String s5 = sh.getString("add", "");

        addCropView(s1, s2, s3, s4, s5);

    }


    private void addCropView(String cropName1, String plantingDate, String soilType, String otherInfo, String address) {
        // Inflate the crop view layout
        View cropView = LayoutInflater.from(this).inflate(R.layout.crop_view, null);

        // Find views within the inflated crop view layout
        TextView textViewCropName = cropView.findViewById(R.id.textViewCropName);
        TextView textViewPlantingDate = cropView.findViewById(R.id.textViewPlantingDate);
        TextView textViewSoilType = cropView.findViewById(R.id.textViewSoilType);
        TextView textViewOtherInfo = cropView.findViewById(R.id.textViewOtherInfo);
        TextView textViewAddress = cropView.findViewById(R.id.textViewAddress);
        ImageButton buttonEdit = cropView.findViewById(R.id.buttonEdit);
        ImageButton buttonDelete = cropView.findViewById(R.id.buttonDelete);

        // Set crop details
        textViewCropName.setText(cropName1);
        textViewPlantingDate.setText(plantingDate);
        textViewSoilType.setText(soilType);
        textViewOtherInfo.setText(otherInfo);
        textViewAddress.setText("Location: " + address);

        // Set click listeners for edit and delete buttons
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle edit action
                // You can navigate to an edit activity or show a dialog to edit the details
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle delete action
                // You can prompt the user for confirmation before deleting the crop details
                // Once confirmed, remove the crop details from SharedPreferences and the view
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("_cropName");
                editor.remove("_plantingDate");
                editor.remove("_soilType");
                editor.remove("_otherInfo");
                editor.remove("_address");
                editor.apply();
                // Remove the view from the parent layout
                parentLayout.removeView(cropView);
            }
        });

        // Add the inflated crop view to the parent layout
        parentLayout.addView(cropView);
    }

    private void saveCropDetails(String cropName, String plantingDate, String soilType, String otherInfo, String address) {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        // Creating an Editor object to edit(write to the file)
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        // Storing the key and its value as the data fetched from edittext
        myEdit.putString("crop", cropName);
        myEdit.putString("plant", plantingDate);
        myEdit.putString("soil", soilType);
        myEdit.putString("info", otherInfo);
        myEdit.putString("add", address);

        myEdit.commit();


    }

}
