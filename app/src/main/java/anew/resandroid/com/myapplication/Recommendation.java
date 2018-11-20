package anew.resandroid.com.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Recommendation extends ResultsActivity {

    private String address;
    private String user;
    private Boolean preference;

    Recommendation (String user, String address, Boolean preference){
        this.user = user;
        this.address = address;
        this.preference = preference;
    }

    protected void savePreferences(){

        SQLiteDatabase restaurants = openOrCreateDatabase("restaurants.db", MODE_PRIVATE,null);

//        Log.d("ERRORS", this.user);


        restaurants.execSQL("CREATE TABLE IF NOT EXISTS Preferences(FBid VARCHAR(50), Address VARCHAR(50), Likes Boolean);");
        restaurants.execSQL("INSERT INTO Preferences VALUES(this.user, this.address, this.preference);");

        Log.d("ERRORS", "called in saveprefrences");
        Log.d("ERRORS", "called in prefrences");


        Cursor resultSet = restaurants.rawQuery("Select * from Preferences", null);

        Log.d("ERRORS", "called in cursor");

        resultSet.moveToFirst();
            while (resultSet.isAfterLast() == false)
            {
                String fbId = resultSet.getString(0);
                Log.d("ERRORS", fbId);

                resultSet.moveToNext();
            }
        }
    }
