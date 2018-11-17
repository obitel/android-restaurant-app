package anew.resandroid.com.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.maps.GoogleMap;

public class SearchCustomer implements ILocation {
    private Location location = null;
    private float searchRadius = (float) 2.0;

    private  GoogleMap gMap;

    SearchCustomer(){}

    SearchCustomer(Location l){
        this.location = l;

    }

    @Override
    public void findLocation() {

    }

    @Override
    public void setLocation(Location l) {
        this.location = l;
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    public void updateSearchRadius(float radius){
        this.searchRadius = radius;
    }

    public void setMap(GoogleMap map){
        this.gMap = map;
    }
}
