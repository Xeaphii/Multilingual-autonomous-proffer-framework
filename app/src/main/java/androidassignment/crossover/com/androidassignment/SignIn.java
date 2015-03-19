package androidassignment.crossover.com.androidassignment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Sunny on 3/19/2015.
 */

public class SignIn extends Activity {
    TextView signUp, forgotPassword;
    EditText UserName, password;
    Button LoginUser;
    DatabaseHelper db;
    SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        Initialization();
    }

    private void Initialization() {
        prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String InitStatus = prefs.getString("is_initialized", "0");
        if (InitStatus.equals("1")) {
            Intent GoToMainActivity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(GoToMainActivity);
            finish();
        }
        db = new DatabaseHelper(getApplicationContext());
        signUp = (TextView) findViewById(R.id.create_user);
        UserName = (EditText) findViewById(R.id.etUserName);
        password = (EditText) findViewById(R.id.etPass);
        forgotPassword = (TextView) findViewById(R.id.forgot_password);
        LoginUser = (Button) findViewById(R.id.btnSingIn);
        LoginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.VerifyUser(new UserProfile(UserName.getText().toString(), Password.getHash(password.getText().toString())))) {
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

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUp = new Intent(getApplicationContext(), SignUp.class);
                startActivity(signUp);
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ForgotPass = new Intent(getApplicationContext(), ForgotPassword.class);
                startActivity(ForgotPass);
            }
        });
    }

    class AddTempItems extends AsyncTask<Void, Integer, String> {
        protected void onPreExecute() {
            Log.d("PreExceute", "On pre Exceute......");
        }

        protected String doInBackground(Void... arg0) {
            Log.d("DoINBackGround", "On doInBackground...");


            return "You are at PostExecute";
        }

        protected void onPostExecute(String result) {

        }
    }
}
