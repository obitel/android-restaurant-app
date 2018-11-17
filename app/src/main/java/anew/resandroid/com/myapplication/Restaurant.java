package anew.resandroid.com.myapplication;

import android.location.Location;

import com.google.android.gms.location.places.Place;

public class Restaurant implements ILocation {

    private String restaurantName;
    private String phoneNumber;
    private String pricePoint;
    private String address;
    private float rating;
    Boolean liked = null;

    Location location = null;

    public Restaurant(Place p)
    {
        restaurantName = p.getName().toString();
        phoneNumber = p.getPhoneNumber().toString();
        address = p.getAddress().toString();
        rating = p.getRating();
        makePricePointString(p.getPriceLevel());
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

    private void makePricePointString(int price)
    {
        StringBuilder priceString = new StringBuilder();
        for(int i = 0; i < price; i++){
            priceString.append("$");
        }

        this.pricePoint = priceString.toString();
    }

    public String getAddress() {
        return address;
    }

    public float getRating() {
        return rating;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPricePoint() {
        return pricePoint;
    }
}
