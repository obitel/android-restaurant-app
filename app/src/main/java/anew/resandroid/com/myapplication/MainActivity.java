package anew.resandroid.com.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = this.loadLogInButton();

        Button smsButton = this.loadSMSButton();


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("check", "button has been clicked");
            }
        });

        smsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("check", "sms button has been clicked");
            }
        });
    }

    protected Button loadLogInButton(){
        Button button = findViewById(R.id.login_button);
        return button;
    }

    protected Button loadSMSButton(){
        Button button = findViewById(R.id.sms_button);
        return button;
    }
}
