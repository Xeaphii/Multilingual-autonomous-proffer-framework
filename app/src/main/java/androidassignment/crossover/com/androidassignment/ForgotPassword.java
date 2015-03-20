package androidassignment.crossover.com.androidassignment;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Sunny on 3/19/2015.
 */
public class ForgotPassword extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
