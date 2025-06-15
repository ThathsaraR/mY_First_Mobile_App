package com.S23010460;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Geocoder;
import android.location.Address;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapUI extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EditText editTextAddress;
    private Button btngetLoc;
    private Geocoder geocoder;
    private Button btn_back;
    private Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_ui);

        editTextAddress = findViewById(R.id.editTextAddress);
        btngetLoc = findViewById(R.id.btngetLoc);
        geocoder = new Geocoder(this, Locale.getDefault());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        btngetLoc.setOnClickListener(v -> {
            String locationName = editTextAddress.getText().toString().trim();
            if (locationName.isEmpty()) {
                Toast.makeText(this, "Please enter location address", Toast.LENGTH_SHORT).show();
            } else {
                findLocation(locationName);
            }
        });

        btn_back = findViewById(R.id.btn_back);
        btn_next = findViewById(R.id.btn_next);
        btn_back.setOnClickListener(view -> {
            startActivity(new Intent(MapUI.this, LoginUI.class));
        });

        btn_next.setOnClickListener(view -> {
            startActivity(new Intent(MapUI.this, TempUI.class));
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        LatLng defaultLocation = new LatLng(6.9271, 79.8612);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10));
    }

    private void findLocation(String addressName) {
        try {
            List<Address> addressList = geocoder.getFromLocationName(addressName, 1);
            if (addressList != null && !addressList.isEmpty()) {
                Address address = addressList.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng).title(addressName));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            } else {
                Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Geocoder error", Toast.LENGTH_SHORT).show();
        }
    }
}