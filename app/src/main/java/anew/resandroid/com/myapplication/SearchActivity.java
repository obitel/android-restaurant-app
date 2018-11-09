package anew.resandroid.com.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    //private FusedLocationProviderClient mFusedLocationProviderClient;
    final int MY_PERMISSIONS_CODE = 1017;
    public static final String PERMISSION_STRING = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private SearchService search;
    private boolean bound = false;

    Location lastLocation = null;
    private LocationManager locationManager;
    private LocationListener locationListener;

    String street = null;
    String city = null;
    String zip = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(ContextCompat.checkSelfPermission(this, this.PERMISSION_STRING) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{this.PERMISSION_STRING}, MY_PERMISSIONS_CODE);
        }
        else{

            String toastString = "Permission Granted!";
            Toast toast = Toast.makeText(getApplicationContext(), toastString, Toast.LENGTH_LONG);
            toast.show();
        }

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if(lastLocation == null){
                    lastLocation = location;
                }
                lastLocation = location;

                locationManager.removeUpdates(locationListener);

                String toastString = String.valueOf(lastLocation.getLongitude()) + ", " + String.valueOf(lastLocation.getLatitude());
                Toast toast = Toast.makeText(getApplicationContext(), toastString, Toast.LENGTH_LONG);
                toast.show();

                locationSearch(lastLocation);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        setContentView(R.layout.activity_search);


        final Button search = findViewById(R.id.search);
        View.OnClickListener searchClickListener;
        search.setOnClickListener(searchClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(SearchActivity.this, PERMISSION_STRING) == PackageManager.PERMISSION_GRANTED) {
                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    String provider = locationManager.getBestProvider(new Criteria(), true);
                    if (provider != null) {
                        locationManager.requestLocationUpdates(provider, 1000, 1, locationListener);
                    }
                }

            }
        });


        Intent intent = getIntent();
    }

    private void locationSearch(Location location){
        List<Address> addressList = null;

        if (location != null) {
            Geocoder geocoder = new Geocoder(SearchActivity.this);
            try{
                addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                Address address = addressList.get(0);

                String toastString = String.valueOf(address);
                Toast toast = Toast.makeText(getApplicationContext(), toastString, Toast.LENGTH_LONG);
                toast.show();

//                ListView searchResults = findViewById(R.id.results);
//
//                searchResults.setVisibility(View.VISIBLE);
//
//                Address[] addressArray = (Address[]) addressList.toArray();
//
//                ArrayAdapter<Address> addressArrayAdapter = new ArrayAdapter<Address>(this, android.R.layout.simple_list_item_1, addressArray);
//
//                searchResults.setAdapter(addressArrayAdapter);

            }catch (IOException e){
                e.printStackTrace();
            }
        }




    }


}

