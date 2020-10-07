package com.example.gpsdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //Initialize variable
    Button btLocation;
    TextView textView1,textView2,textView3,textView4,textView5;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign variable
        btLocation = findViewById(R.id.bt_location);
        textView1 = findViewById(R.id.text_view1);
        textView2 = findViewById(R.id.text_view2);
        textView3 = findViewById(R.id.text_view3);
        textView4 = findViewById(R.id.text_view4);
        textView5 = findViewById(R.id.text_view5);

        //Initialize fusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        btLocation.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Check permissions
                if (ActivityCompat.checkSelfPermission(MainActivity.this
                    , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    //When permission granted
                    getLocation();
                } else {
                    //When permission denied
                    ActivityCompat.requestPermissions(MainActivity.this
                        ,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);

                }
            }

            private void getLocation() {
                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        //Initialize Location
                        Location location = task.getResult();
                        if (location != null){
                            try {
                                //initialize geo coder
                                Geocoder geocoder = new Geocoder(MainActivity.this,
                                            Locale.getDefault());//Initialize address list
                                List<Address> addresses = geocoder.getFromLocation(
                                        location.getLatitude(),location.getLongitude(), 1
                                );
                                //Set TextViews
                                textView1.setText("Latitude : " + addresses.get(0).getLatitude());
                                textView2.setText("Longitude : " + addresses.get(0).getLongitude());
                                textView3.setText("Country : " + addresses.get(0).getCountryName());
                                textView4.setText("Locality : " + addresses.get(0).getLocality());
                                textView5.setText("Address : " + addresses.get(0).getAddressLine(0));

                        } catch (IOException e) {
                                e.printStackTrace();
                            }
                    }
                ;
            }
        });

    }

    });
    }}