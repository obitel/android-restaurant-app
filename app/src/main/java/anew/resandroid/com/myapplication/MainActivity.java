package anew.resandroid.com.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private String name;
    private String picId;
    private String fbId;
    private String on_resume;
    private ProfilePictureView profilePictureView;
    Profile profile;

    public static final String PREFS_NAME = "MY_PREFS";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView mTextView = findViewById(R.id.welcome);

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFromScreen();
            }
            
        });

        if (savedInstanceState == null) {
            mTextView.setText(R.string.welcome);
        } else {
            on_resume = savedInstanceState.getString("name");
            mTextView.setText("Welcome back." + on_resume);
        }

        loginButton = findViewById(R.id.login_button);

        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));

       Button button = this.loadLogInButton();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogin();
            }

        });


        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                       AccessToken accessToken = loginResult.getAccessToken();
                       profile = Profile.getCurrentProfile();

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        Log.v("LoginActivity", response.toString());

                                        try {
                                            String email = object.getString("email");
                                            String birthday = object.getString("birthday");

                                            JSONObject responseGraphObject = response.getJSONObject();
                                            name = (String) responseGraphObject.get("name");
                                            fbId = (String) responseGraphObject.get("id");

                                            TextView textView = findViewById(R.id.welcome);
                                            textView.setText( R.string.welcome + ", " + name);

                                            ProfilePictureView profilePictureView;

                                            profilePictureView = findViewById(R.id.friendProfilePicture);

                                            profilePictureView.setProfileId(fbId);

                                            SharedPreferences preferences =  PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                              SharedPreferences.Editor editor=preferences.edit();
                                              editor.putString("Name",fbId);
                                              editor.commit();Log.d("ERRORS", name);

                                        } catch (Exception e){
                                            Log.d("ERRORS", e.getMessage());
                                        }
                                        Log.d("ERRORS", response.toString());
                                    }
                                });

                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender,birthday");
                        request.setParameters(parameters);
                        request.executeAsync();
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

    }

    protected Button loadLogInButton() {
        Button button = findViewById(R.id.login_button);
        return button;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    private final void goToLogin () {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public static Bitmap getFacebookProfilePicture(String userID) throws IOException {
        String fbImg = "https://graph.facebook.com/"+ userID +"/picture?type=large";
        URL imageURL = new URL(fbImg);
        Bitmap bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());

        return bitmap;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("name", on_resume);
    }

    private final void startFromScreen () {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);                                            
    }

}

