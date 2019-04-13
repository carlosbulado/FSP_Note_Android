package com.carlosbulado.fsp_note.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.carlosbulado.fsp_note.R;
import com.carlosbulado.fsp_note.app.APP;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback
{

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        if(NotePageActivity.note != null)
        {
            LatLng myLoc = new LatLng(NotePageActivity.note.getLatitude(), NotePageActivity.note.getLongitude());
            mMap.addMarker(new MarkerOptions().position(myLoc).title("My Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLoc, 10));
        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener()
        {
            @Override
            public void onMapClick(LatLng latLng)
            {
                mMap.clear();

                mMap.addMarker(new MarkerOptions().position(latLng).title("My Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                NotePageActivity.note.setLatitude(latLng.latitude);
                NotePageActivity.note.setLongitude(latLng.longitude);

                Log.i("My Location", "lat: " + latLng.latitude + " / Long: " + latLng.longitude);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_note_page_activity, menu);
        menu.getItem(2).setVisible(false);
        menu.getItem(3).setVisible(false);
        menu.getItem(4).setVisible(false);
        menu.getItem(5).setVisible(false);
        menu.getItem(6).setVisible(false);
        menu.getItem(7).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_note_page_save:
                APP.Services.noteService.save(NotePageActivity.note);
            case R.id.menu_note_page_cancel:
                Intent nextPage = new Intent(this, NotePageActivity.class);
                nextPage.putExtra("noteId", NotePageActivity.note.getId());
                this.startActivity(nextPage);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
