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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sunny on 3/19/2015.
 */
public class SignUp extends Activity {
    TextView loginIn;
    EditText UserName, password, email;
    Button SignUpUser;
    DatabaseHelper db;
    SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs";

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
        getActionBar().setDisplayHomeAsUpEnabled(true);
        password = (EditText) findViewById(R.id.etPass);
        email = (EditText) findViewById(R.id.etEmail);
        SignUpUser = (Button) findViewById(R.id.btnSingUp);
        SignUpUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmailValid(email.getText().toString().trim())) {
                    if (UserName.getText().toString().trim().length() > 5) {
                        if (password.getText().toString().trim().length() > 5) {
                            Boolean check = false;

                            check = db.InsertUser(new UserProfile(
                                    email.getText().toString().trim(),
                                    UserName.getText().toString().trim(),
                                    Password.getHash(password.getText().toString().trim())));

                            if (check) {
                                prefs.edit().putString("is_initialized", "1").commit();
                                prefs.edit().putString("user_name", UserName.getText().toString()).commit();
                                Intent signUp = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(signUp);
                                finish();
                            } else {
                                ShowToast("UserName already exist.");
                            }
                        } else {
                            ShowToast("Password should be greater than 5 length.");
                        }

                    } else {
                        ShowToast("Username should be greater than 5 length.");
                    }

                } else {
                    ShowToast("Wrong email address.");

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

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    private void ShowToast(String input) {
        Toast.makeText(getApplicationContext(), input, Toast.LENGTH_SHORT).show();
    }
}
