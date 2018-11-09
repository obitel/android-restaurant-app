package anew.resandroid.com.myapplication;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.content.Context;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;

public class SearchService extends Service {
    private LocationManager locationManager;
    public static final String PERMISSION_STRING = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private LocationListener listener;
    private final IBinder binder = new SearchBinder();
    private static Location lastLocation = null;

    @Override
    public void onCreate(){
        super.onCreate();
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if(lastLocation == null){
                    lastLocation = location;
                }
                lastLocation = location;

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

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(this, PERMISSION_STRING) == PackageManager.PERMISSION_GRANTED){
            String provider = locationManager.getBestProvider(new Criteria(), true);
            if(provider != null){
                locationManager.requestLocationUpdates(provider, 1000, 1, listener);
            }
        }
    }

    public class SearchBinder extends Binder {
        SearchService getSearch(){
            return SearchService.this;
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationManager != null && listener != null){
            if(ContextCompat.checkSelfPermission(this, PERMISSION_STRING) == PackageManager.PERMISSION_GRANTED){
                locationManager.removeUpdates(listener);
            }
            locationManager = null;
            listener = null;
        }
    }
}
