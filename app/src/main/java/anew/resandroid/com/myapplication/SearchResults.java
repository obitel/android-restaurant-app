package anew.resandroid.com.myapplication;

import java.util.ArrayList;

public class SearchResults {
    public static Restaurant[] restaurants;

    public SearchResults(ArrayList<Restaurant> restaurantArrayList) {
        restaurants = new Restaurant[restaurantArrayList.size()];
        restaurants = (Restaurant[]) restaurantArrayList.toArray();
    }
}
