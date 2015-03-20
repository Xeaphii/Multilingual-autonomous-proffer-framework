package androidassignment.crossover.com.androidassignment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sunny on 3/19/2015.
 */
public class ForgotPassword extends Activity {
    Button Retrieve;
    EditText UserName, Email;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        Retrieve = (Button) findViewById(R.id.btn_retrieve);
        UserName = (EditText) findViewById(R.id.etUserName);
        Email = (EditText) findViewById(R.id.etEmail);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        db = new DatabaseHelper(getApplicationContext());
        Retrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmailValid(Email.getText().toString().trim())) {
                    if (UserName.getText().toString().trim().length() > 5) {
                        if (db.ForgotPassword(UserName.getText().toString().trim(),
                                Email.getText().toString().trim())) {
                            RetrivePasswordDialog();
                        } else {
                            ShowToast("No record found.");
                        }

                    } else {
                        ShowToast("Username should be greater than 5 length.");
                    }

                } else {
                    ShowToast("Wrong email address.");

                }
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

    private void RetrivePasswordDialog() {
        LayoutInflater li = LayoutInflater.from(ForgotPassword.this);
        View promptsView = li.inflate(R.layout.password_retrieval_dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                ForgotPassword.this);


        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.etPass);


        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text
                                if (userInput.getText().toString().trim().length() > 5) {
                                    db.ChangeUserPassword(UserName.getText().toString().trim(),
                                            Password.getHash(userInput.getText().toString().trim()));
                                    finish();
                                } else {
                                    ShowToast("Password should be greater than 5 length.");

                                }

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ShowToast("Password unchanged.");
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }
}
