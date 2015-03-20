package androidassignment.crossover.com.androidassignment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Sunny on 3/19/2015.
 */
public class ChangePassword extends Activity {
    EditText OldPassword, NewPassword;
    Button changePassword;
    DatabaseHelper db;
    SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        db = new DatabaseHelper(getApplicationContext());
        prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        OldPassword = (EditText) findViewById(R.id.et_old_pass);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        NewPassword = (EditText) findViewById(R.id.et_pass);
        changePassword = (Button) findViewById(R.id.bt_change_password);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UserName = prefs.getString("user_name", "");
                if (!(OldPassword.getText().toString().trim().equals(NewPassword.getText().toString().trim())) &&
                        !(NewPassword.getText().toString().trim().equals(""))) {
                    if (OldPassword.getText().toString().trim().length() > 5) {
                        if (NewPassword.getText().toString().trim().length() > 5) {
                            if (db.VerifyUser(new UserProfile(UserName, Password.getHash(OldPassword.getText().toString
                                    ())))) {
                                prefs.edit().putString("is_initialized", "0").commit();
                                prefs.edit().putString("user_name", "").commit();
                                db.ChangeUserPassword(UserName, Password.getHash(NewPassword.getText().toString().trim()));
                                finish();
                            } else {
                                ShowToast("Wrong old password");
                            }
                        } else {
                            ShowToast("New Password should be greater than 5 length.");
                        }
                    } else {
                        ShowToast("Old Password should be greater than 5 length.");
                    }

                } else {
                    ShowToast("Modify new password");
                }
            }
        });
    }

    private void ShowToast(String input) {
        Toast.makeText(getApplicationContext(), input, Toast.LENGTH_SHORT).show();
    }
}
