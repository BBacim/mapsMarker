package com.example.mahmoud.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, AdapterView.OnItemClickListener {

    private GoogleMap mMap;
    int pos;
    int[] lat,lng;
    String[] salles;
    String title;
    int lati,lngi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        int layoutItemId = android.R.layout.simple_dropdown_item_1line;
        // matrix contains : name | Lat | Long
        String [][] matrix =  { {"Class1","45","5646"}, {"Class2","-20","-20"}, {"Class3","2","3"} };
        salles = new String[matrix.length];
        lat = new int[matrix.length];
        lng = new int[matrix.length];

        for(int i=0; i<matrix.length;i++)
        {
            salles[i]=matrix[i][0];
            lat[i]= Integer.parseInt(matrix[i][1]);
            lng[i]= Integer.parseInt(matrix[i][2]);

        }
        List<String> Classrooms = Arrays.asList(salles);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, layoutItemId, Classrooms);

            AutoCompleteTextView autocompleteView =
                (AutoCompleteTextView) findViewById(R.id.autocompleteView);

        autocompleteView.setAdapter(adapter);

        autocompleteView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(MapsActivity.this,
                        adapter.getItem(position).toString(),
                        Toast.LENGTH_SHORT).show();
                pos = position;
                onMapReady(mMap);
            }
        });


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }




    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //affeting clicked clasroom
        for(int i=0; i<salles.length;i++)
        {
            title = salles[pos];
            lati = lat[pos];
            lngi =lng[pos];

        }
        //adding marker
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(lati, lngi))
                .title(title));
        //centering the camera
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(lati, lngi))
                .zoom(15)
                .bearing(0)
                .tilt(45)
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}