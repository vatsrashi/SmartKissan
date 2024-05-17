package com.example.smartkissan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class TransportMap extends AppCompatActivity implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final String PREF_ENTERED_ADDRESS = "entered_address";

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private TextView coordinatesTextView;
    private Geocoder geocoder;
    private EditText searchEditText;
    private LatLng userLocation;
    ImageView back;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_map);

        coordinatesTextView = findViewById(R.id.coordinatesTextViewMap2);
        searchEditText = findViewById(R.id.searchEditTextMap2);
        back = findViewById(R.id.back);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Button btnSubmit = findViewById(R.id.btnSubmitMap2);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userLocation != null) {
                    String address = getAddressFromLocation(userLocation.latitude, userLocation.longitude);
                    // Pass the selected location back to BookTransport activity
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("selected_location", address);
                    setResult(RESULT_OK, resultIntent);
                    finish(); // Finish the TransportMap activity
                } else {
                    Toast.makeText(TransportMap.this, "Please select a location on the map", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            initMap();
        }

        searchEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String address = searchEditText.getText().toString().trim();
                    if (!address.isEmpty()) {
                        geocode(address);
                        return true;
                    }
                }
                return false;
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(TransportMap.this, BookTransport.class);
                startActivity(back);
            }
        });
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragmentMap2);
        mapFragment.getMapAsync(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        geocoder = new Geocoder(this, Locale.getDefault());
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (userLocation == null) {
                        userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        moveCamera(userLocation, 15);
                        updateCoordinatesTextView(userLocation.latitude, userLocation.longitude);
                    }
                }
            }
        };
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mMap.clear();
                addMarker(latLng.latitude, latLng.longitude);
                userLocation = latLng;
                updateCoordinatesTextView(latLng.latitude, latLng.longitude);
                reverseGeocode(latLng.latitude, latLng.longitude);
            }
        });

        String enteredAddress = sharedPreferences.getString(PREF_ENTERED_ADDRESS, "");
        if (!enteredAddress.isEmpty()) {
            geocode(enteredAddress);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void startLocationUpdates() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.requestLocationUpdates(createLocationRequest(), locationCallback, null);
        }
    }

    private void stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    private LocationRequest createLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initMap();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void moveCamera(LatLng latLng, float zoom) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
        mMap.animateCamera(cameraUpdate);
    }

    private void addMarker(double latitude, double longitude) {
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Custom Location"));
    }

    private void updateCoordinatesTextView(double latitude, double longitude) {
        String coordinates = "Lat: " + latitude + ", Lng: " + longitude;
        coordinatesTextView.setText(coordinates);
    }

    private void reverseGeocode(double latitude, double longitude) {
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (!addresses.isEmpty()) {
                String addressLine = addresses.get(0).getAddressLine(0);
                Toast.makeText(this, "Address: " + addressLine, Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getAddressFromLocation(double latitude, double longitude) {
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (!addresses.isEmpty()) {
                Address address = addresses.get(0);
                return address.getAddressLine(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void geocode(String locationName) {
        try {
            List<Address> addresses = geocoder.getFromLocationName(locationName, 1);
            if (!addresses.isEmpty()) {
                Address address = addresses.get(0);
                double latitude = address.getLatitude();
                double longitude = address.getLongitude();
                LatLng latLng = new LatLng(latitude, longitude);
                mMap.clear();
                addMarker(latitude, longitude);
                userLocation = latLng;
                moveCamera(latLng, 15);
                updateCoordinatesTextView(latitude, longitude);
            } else {
                Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
