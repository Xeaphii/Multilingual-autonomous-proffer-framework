package androidassignment.crossover.com.androidassignment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Sunny on 3/19/2015.
 */
public class SignUp extends Activity {
    TextView loginIn;
    EditText UserName, password, email;
    Button SignUpUser;
    DatabaseHelper db;
    SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        Initialization();
    }

    private void Initialization() {
        prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        db = new DatabaseHelper(getApplicationContext());
        loginIn = (TextView) findViewById(R.id.sign_up);
        UserName = (EditText) findViewById(R.id.etUserName);
        password = (EditText) findViewById(R.id.etPass);
        email = (EditText) findViewById(R.id.etEmail);
        SignUpUser = (Button) findViewById(R.id.btnSingUp);
        SignUpUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean check = false;

                check = db.InsertUser(new UserProfile(email.getText().toString(), UserName.getText().toString(), Password.getHash(password.getText().toString())));

                if (check) {
                    prefs.edit().putString("is_initialized", "1").commit();
                    prefs.edit().putString("user_name", UserName.getText().toString()).commit();
                    Intent signUp = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(signUp);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Password or UserName", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUp = new Intent(getApplicationContext(), SignIn.class);
                startActivity(signUp);
            }
        });

    }
}
