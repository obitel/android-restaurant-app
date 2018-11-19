package anew.resandroid.com.myapplication;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.util.Log;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;


public class MainActivity extends AppCompatActivity {

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = this;

//        Button button = this.loadLogInButton();
//
//        Button smsButton = this.loadSMSButton();
//
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Log.d("check", "button has been clicked");
//            }
//
//        });
//
//        smsButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Log.d("check", "sms button has been clicked");
//            }
//        });

        // Get the intent, verify the action and get the query
//        Intent intent = getIntent();
//        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
//            String query = intent.getStringExtra(SearchManager.QUERY);
//            doMySearch(query);
//        }
//
        callbackManager = CallbackManager.Factory.create();
//
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });


//        int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
//        try {
//            Intent intent =
//                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
//                            .build(this);
//
//            Log.d("check", "inside intent");
//
//            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
//        } catch (GooglePlayServicesRepairableException e) {
//            // TODO: Handle the error.
//        } catch (GooglePlayServicesNotAvailableException e) {
//            // TODO: Handle the error.
//        }

        TextView textView = findViewById(R.id.my_search_button);

        textView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), SearchableActivity.class);
                startActivity(intent);
            }
        });












    }

//    protected Button loadLogInButton(){
//        Button button = findViewById(R.id.login_button);
//        return button;
//    }
//
//    protected Button loadSMSButton(){
//        Button button = findViewById(R.id.sms_button);
//        return button;
//    }
    @Override
    public void onStart() {
        super.onStart();

    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

//        Intent intent = getIntent();
//
//        String action = intent.getAction();

                   Log.d("PLACES_LOAD", data.toString());


//        if (requestCode == 1) {
//            if (resultCode == RESULT_OK) {
                Intent intent = new Intent(this, SearchableActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
//            }
//        }


//        Intent intent = new Intent(this, SearchableActivity.class);
//
//        startActivity(intent);



//        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
//            String query = intent.getStringExtra(SearchManager.QUERY);
//            doMySearch(query);
//        }
    }
    protected void doMySearch(String query){
        return;
    }
}
