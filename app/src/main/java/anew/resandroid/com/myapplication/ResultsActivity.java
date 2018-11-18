package anew.resandroid.com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity{

    private RecyclerView mRecyclerView;
    private  RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_results);

        Log.v("Results: ", "checkPoint 1");

        ArrayList<Restaurant> restaurantArrayList;

        Intent intent = getIntent();
        restaurantArrayList = intent.getParcelableArrayListExtra("restaurants");
        Log.v("Results: ", "checkPoint 2\n" + "Array size: " + restaurantArrayList.size());

        Restaurant[] restaurantsArray = new Restaurant[restaurantArrayList.size()];
        for(int i = 0; i < restaurantArrayList.size(); i++) {
            restaurantsArray[i] = restaurantArrayList.get(i);
        }

        Log.v("Results: ", "checkPoint 3");

        mRecyclerView = findViewById(R.id.restaurant_recycler);

        if (mRecyclerView != null) {
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);

            mAdapter = new RestaurantViewAdapter(restaurantsArray);
            mRecyclerView.setAdapter(mAdapter);
        }

        Log.v("Results: ", "checkPoint 4");

    }
}
