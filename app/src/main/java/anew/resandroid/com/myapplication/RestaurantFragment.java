package anew.resandroid.com.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class RestaurantFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        RecyclerView restaurantRecycler = (RecyclerView) inflater.inflate(R.layout.fragment_restaurant, container, false);

        RestaurantViewAdapter restaurantViewAdapter = new RestaurantViewAdapter(SearchResults.restaurants);
        restaurantRecycler.setAdapter(restaurantViewAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        restaurantRecycler.setLayoutManager(layoutManager);

        return restaurantRecycler;

    }


}
