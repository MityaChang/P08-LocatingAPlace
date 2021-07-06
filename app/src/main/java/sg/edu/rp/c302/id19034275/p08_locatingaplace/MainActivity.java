package sg.edu.rp.c302.id19034275.p08_locatingaplace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {
    private GoogleMap map;
    Spinner sp , sp2;

    //Make Array
    String names[] = {"Choose your location","North","Central","East"};
    String types[] = {"Choose map type","Normal","Terrain","Satelite"};
    ArrayAdapter <String> adaptertype;
    ArrayAdapter <String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = (Spinner)findViewById(R.id.spinner);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,names);
        sp.setAdapter(adapter);
        sp2 = (Spinner)findViewById(R.id.spinnerType);
        adaptertype = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,types);
        sp2.setAdapter(adaptertype);
        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment)
                fm.findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                UiSettings ui = map.getUiSettings();

                ui.setZoomControlsEnabled(true);

                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION);

                if (permissionCheck == PermissionChecker.PERMISSION_GRANTED) {
                    map.setMyLocationEnabled(true);
                } else {
                    Log.e("GMap - Permission", "GPS access has not been granted");
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                }
                LatLng poi_HQ = new LatLng(1.461708, 103.813500);
                Marker hq = map.addMarker(new
                        MarkerOptions()
                        .position(poi_HQ)
                        .title("North - HQ")
                        .snippet("Block 333, Admiralty Ave 3, 765654 \n" + "Operating hours: 10am-5pm\n" +
                                "Tel:65433456\n")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.starre)));

                LatLng poi_central = new LatLng(1.300542, 103.841226);
                Marker ct = map.addMarker(new
                        MarkerOptions()
                        .position(poi_central)
                        .title("Central")
                        .snippet("Block 3A, Orchard Ave 3, 134542 \n" +
                                "Operating hours: 11am-8pm\n" +
                                "Tel:67788652\n")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                LatLng poi_east = new LatLng(1.350057, 103.934452);
                Marker et = map.addMarker(new
                        MarkerOptions()
                        .position(poi_east)
                        .title("East")
                        .snippet("Block 555, Tampines Ave 3, 287788 \n" +
                                "Operating hours: 9am-5pm\n" +
                                "Tel:66776677\"\n")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    String markerName = marker.getTitle();
                    Toast.makeText(MainActivity.this,markerName,Toast.LENGTH_LONG).show();
                    return false;
                }
            });
            }
        });
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 1:
                        LatLng pos_north = new LatLng(1.461708, 103.813500);
                        CameraUpdate update_north = CameraUpdateFactory.newLatLngZoom(pos_north, 15);
                        map.moveCamera(update_north);
                        break;
                    case 2:
                        LatLng pos_Middle = new LatLng(1.300542, 103.841226);
                        CameraUpdate update_Middle = CameraUpdateFactory.newLatLngZoom(pos_Middle, 15);
                        map.moveCamera(update_Middle);
                        break;
                    case 3:
                        LatLng pos_east = new LatLng(1.3500557, 103.934452);
                        CameraUpdate update_east = CameraUpdateFactory.newLatLngZoom(pos_east, 15);
                        map.moveCamera(update_east);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 1:
                        if(map != null){
                            map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                        }
                        break;
                    case 2:
                        if(map != null){
                            map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        }
                        break;
                    case 3:
                        if(map != null){
                            map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                        }
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}