package com.yogeshbalan.upahar.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.yogeshbalan.upahar.R;
import com.yogeshbalan.upahar.model.NGO;

public class NGO_DetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    NGO ngo;
    private TextView descriptionTextview, ngoName, ngoSlogan;
    Double lat,lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo__detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        descriptionTextview = (TextView) findViewById(R.id.description_tv);
        ngoName = (TextView) findViewById(R.id.tvNGO_Name);
        ngoSlogan = (TextView) findViewById(R.id.tvNGOSlogan);

        ngo = (NGO) getIntent().getSerializableExtra("ngo");
        Log.v("test", String.valueOf(ngo));

        lat = ngo.getGeopoint().getLat();
        lng = ngo.getGeopoint().getLng();

        ngoName.setText(ngo.getName());
        ngoSlogan.setText(ngo.getSlogan());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                startActivity(new Intent(NGO_DetailActivity.this, UploadAssetsActivity.class));
            }
        });

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(ngo.getName());

        loadBackdrop();
        descriptionTextview.setText(ngo.getDescription() + "\n\n\n\n\t" + ngo.getSlogan());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Picasso.with(this).load(ngo.getImageUrls().get(0)).placeholder(R.drawable.placeholder).into(imageView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ngo__detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.event_call) {
            Toast.makeText(this, "call", Toast.LENGTH_SHORT);
            call();
        }
        if (id == R.id.event_info) {
            Toast.makeText(this, "info", Toast.LENGTH_SHORT);
            goToWebsite();
        }

        return super.onOptionsItemSelected(item);
    }

    private void call() {
        if (ngo.getPhoneNo() != null) {
            Intent in = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + ngo.getPhoneNo()));
            try {
                startActivity(in);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getApplicationContext(), "yourActivity is not founded", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void goToWebsite() {
        if (ngo.getWebsiteUrl() != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(ngo.getWebsiteUrl()));
            startActivity(intent);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng location = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(location).title(ngo.getAddress()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 14.0f));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }
}
