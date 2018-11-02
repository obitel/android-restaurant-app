package anew.resandroid.com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button login = findViewById(R.id.login_button);

        View.OnClickListener onClickLogin = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = getTextFromInput(R.id.edit_email);
                password = getTextFromInput(R.id.edit_pw);

                String toastString = "Trying " + username +"/" + password;
                Toast toast = Toast.makeText(getApplicationContext(), toastString, Toast.LENGTH_LONG);
                toast.show();
                negotiateCredentials(username, password);
            }
        };

        login.setOnClickListener(onClickLogin);

    }

    private void negotiateCredentials(String un, String pw)
    {
        if((un.equals("admin")) && (pw.equals("admin"))){
            goToSearch();
        }
    }

    private void goToSearch()
    {
        Intent toSearch = new Intent(LoginActivity.this, SearchActivity.class);
        startActivity(toSearch);

    }

    private String getTextFromInput(int id){
        String inputString = "";
        final EditText userIn = findViewById(id);
        if (userIn != null){inputString = userIn.getText().toString();}

        return inputString;
    }

    Intent intent = getIntent();
}
