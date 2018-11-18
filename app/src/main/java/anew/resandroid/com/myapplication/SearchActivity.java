package anew.resandroid.com.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    //private FusedLocationProviderClient mFusedLocationProviderClient;
    final int MY_PERMISSIONS_CODE = 1017;
    final int PLACE_PICKER_REQUEST = 1;
    public static final String PERMISSION_STRING = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private boolean bound = false;

    Location lastLocation = null;
    private LocationManager locationManager;
    private LocationListener locationListener;


    String street = null;
    String city = null;
    String zip = null;

    TextView rName;
    TextView rType;
    TextView rDistance;
    TextView rPrice;
    TextView rRating;
    LinearLayout rLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(ContextCompat.checkSelfPermission(this, this.PERMISSION_STRING) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{this.PERMISSION_STRING}, MY_PERMISSIONS_CODE);
        }

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //GETS LOCATION. NOT REALLY NEEDED, BUT MIGHT BE USEFUL
                if(lastLocation == null){
                    lastLocation = location;
                }
                lastLocation = location;

                locationManager.removeUpdates(locationListener);

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


        rName = findViewById(R.id.restaurantName);
        rType = findViewById(R.id.restaurantType);
        rDistance = findViewById(R.id.restaurantDistance);
        rPrice = findViewById(R.id.restaurantPrice);
        rRating = findViewById(R.id.restaurantRating);
        rLayout = findViewById(R.id.restaurantLayout);


        final Button search = findViewById(R.id.search);
        View.OnClickListener searchClickListener;
        search.setOnClickListener(searchClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(SearchActivity.this, PERMISSION_STRING) == PackageManager.PERMISSION_GRANTED) {
                    locationSearch();


/*Code for getting devices Latitude and Longitude. Unnecessary with google Maps*/
//                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//                    String provider = locationManager.getBestProvider(new Criteria(), true);
//                    Log.v("Place", "Checkpoint2");
//                    if (provider != null) {
//                        Log.v("Place", "Checkpoint3");
//                        locationManager.requestLocationUpdates(provider, 1000, 1, locationListener);
//                    }
                }
                else{
                    //Read string from above.
                }

            }
        });


        Intent intent = getIntent();
    }

    private void locationSearch(){
        final ArrayList<Restaurant> restaurantList = new ArrayList<>();

        if(ContextCompat.checkSelfPermission(this, this.PERMISSION_STRING) == PackageManager.PERMISSION_GRANTED) {
            PlaceDetectionClient placeDetectionClient = Places.getPlaceDetectionClient(SearchActivity.this);

            Task<PlaceLikelihoodBufferResponse> placeLikelihoods = placeDetectionClient.getCurrentPlace(null);
            placeLikelihoods.addOnCompleteListener(new OnCompleteListener<PlaceLikelihoodBufferResponse>() {
                @Override
                public void onComplete(@NonNull Task<PlaceLikelihoodBufferResponse> task) {
                    List<Place> placeList = new ArrayList<Place>();
                    PlaceLikelihoodBufferResponse possiblePlaces = task.getResult();

                    for(PlaceLikelihood placeLikelihood : possiblePlaces){
                        placeList.add(placeLikelihood.getPlace().freeze());
                    }

                    possiblePlaces.release();

                    for(Place place : placeList){
                        List<Integer> types = place.getPlaceTypes();
                        for(int type: types){
                            if(type == Place.TYPE_RESTAURANT){
                                Restaurant restaurant = new Restaurant(place);
                                restaurantList.add(restaurant);

                                break;
                            }
                        }

                    }

                    Intent toResults = new Intent(SearchActivity.this, ResultsActivity.class);
                    toResults.putParcelableArrayListExtra("restaurants", restaurantList);
                    startActivity(toResults);
                }
            });
        }

    }

}

