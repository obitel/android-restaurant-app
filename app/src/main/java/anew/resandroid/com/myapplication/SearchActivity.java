package anew.resandroid.com.myapplication;

import android.Manifest;
import android.bluetooth.BluetoothClass;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import android.os.Bundle;
import android.widget.ToggleButton;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class SearchActivity extends AppCompatActivity {

    private FusedLocationProviderClient mFusedLocationProviderClient;

    String street = null;
    String city = null;
    String zip = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling -- GET PERMISSIONS
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            String toastString = "Hey dude, you need to allow location";
            Toast toast = Toast.makeText(getApplicationContext(), toastString, Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        mFusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            String toastString = location.toString();
                            Toast toast = Toast.makeText(getApplicationContext(), toastString, Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                });


        final Button search = findViewById(R.id.search);
        search.setOnClickListener(onClickSearch);

        final ToggleButton locationSwitch = (ToggleButton)findViewById(R.id.locationSwitch);
        locationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //Get Location
                }
            }
        });

        Intent intent = getIntent();
    }


    private View.OnClickListener onClickSearch = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            street = getTextFromInput(R.id.street);
            city = getTextFromInput(R.id.city);
            zip = getTextFromInput(R.id.zip);


        }
    };

    private String getTextFromInput(int id){
        String inputString = "";
        final EditText userIn = findViewById(id);
        if (userIn != null){inputString = userIn.getText().toString();}

        return inputString;
    }

}

