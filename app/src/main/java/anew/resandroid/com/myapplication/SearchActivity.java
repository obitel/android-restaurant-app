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

    private SearchCustomer searchCustomer = null;

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
        else{

            String toastString = "Permission Granted!";
            Toast toast = Toast.makeText(getApplicationContext(), toastString, Toast.LENGTH_LONG);
            toast.show();
        }

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.v("Place", "Checkpoint4");
                if(lastLocation == null){
                    lastLocation = location;
                }
                lastLocation = location;

                Log.v("Place", "Checkpoint5");

                locationManager.removeUpdates(locationListener);

                String toastString = String.valueOf(lastLocation.getLongitude()) + ", " + String.valueOf(lastLocation.getLatitude());
                Toast toast = Toast.makeText(getApplicationContext(), toastString, Toast.LENGTH_LONG);
                toast.show();

                searchCustomer = new SearchCustomer(location);

                Log.v("Place", "Checkpoint6");

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
                Log.v("Place", "Checkpoint1");

                if (ContextCompat.checkSelfPermission(SearchActivity.this, PERMISSION_STRING) == PackageManager.PERMISSION_GRANTED) {
                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    String provider = locationManager.getBestProvider(new Criteria(), true);
                    Log.v("Place", "Checkpoint2");
                    if (provider != null) {
                        Log.v("Place", "Checkpoint3");
                        locationManager.requestLocationUpdates(provider, 1000, 1, locationListener);
                    }
                }
                else{
                    //Read string from above.
                }

            }
        });


        Intent intent = getIntent();
    }

    private void locationSearch(Location location){

        if(ContextCompat.checkSelfPermission(this, this.PERMISSION_STRING) == PackageManager.PERMISSION_GRANTED) {
            Log.v("Place", "Checkpoint7");
            PlaceDetectionClient placeDetectionClient = Places.getPlaceDetectionClient(SearchActivity.this);
            Log.v("Place", "Checkpoint8");
            Task<PlaceLikelihoodBufferResponse> placeLikelihoods = placeDetectionClient.getCurrentPlace(null);
            placeLikelihoods.addOnCompleteListener(new OnCompleteListener<PlaceLikelihoodBufferResponse>() {
                @Override
                public void onComplete(@NonNull Task<PlaceLikelihoodBufferResponse> task) {
                    Log.v("Place", "Checkpoint9");
                    Log.v("Tag", "Current Location Info");
                    List<Place> placeList = new ArrayList<Place>();
                    PlaceLikelihoodBufferResponse possiblePlaces = task.getResult();
                    Log.v("Place", "Checkpoint10");
                    for(PlaceLikelihood placeLikelihood : possiblePlaces){
                        placeList.add(placeLikelihood.getPlace().freeze());
                    }
                    Log.v("Place", "Checkpoint11");
                    possiblePlaces.release();

                    ArrayList<Restaurant> restaurantList = new ArrayList<>();

                    for(Place place : placeList){
                        List<Integer> types = place.getPlaceTypes();
                        for(int type: types){
                            if(type == Place.TYPE_RESTAURANT){
                                Restaurant restaurant = new Restaurant(place);
                                restaurantList.add(restaurant);

                                rName.setText(place.getName().toString());

                                StringBuilder priceString = new StringBuilder();
                                for(int i = 0; i < place.getPriceLevel(); i++){
                                    priceString.append("$");
                                }
                                rPrice.setText(priceString.toString());

                                //TODO: Find way to calculate distance

                                rRating.setText(String.valueOf(place.getRating()));

                                break;
                            }
                        }
                        rLayout.setVisibility(View.VISIBLE);

                    }

                    for(Restaurant r : restaurantList){
                        Log.v("Places", r.getRestaurantName() + " " + r.getPricePoint() + " " + r.getRating());

                    }
                }
            });
        }

//        List<Address> addressList = null;
//
//        if (location != null) {
//            Geocoder geocoder = new Geocoder(SearchActivity.this);
//            try{
//                addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//
//                Address address = addressList.get(0);
//
//                String toastString = String.valueOf(address);
//                Toast toast = Toast.makeText(getApplicationContext(), toastString, Toast.LENGTH_LONG);
//                toast.show();
//
//
//
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//        }

    }

}

