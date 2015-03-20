package androidassignment.crossover.com.androidassignment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Sunny on 3/20/2015.
 */
public class DateTimePickerActivity extends Activity {

    TimePicker timePicker;
    private final static String DATE_TIME_STRING = "date_time_string";
    DatePicker datePicker;
    Button Set;
    int hour = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_time_picker);
        timePicker = (TimePicker) findViewById(R.id.time_picker);
        datePicker = (DatePicker) findViewById(R.id.date_picker);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                hour = hourOfDay;
            }
        });
        Set = (Button) findViewById(R.id.date_time_set);
        Set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                        datePicker.getMonth(),
                        datePicker.getDayOfMonth(),
                        hour,
                        timePicker.getCurrentMinute());



                Intent returnIntent = new Intent();
                returnIntent.putExtra(DATE_TIME_STRING,getDate(calendar.getTimeInMillis(), "yyyy-MM-dd hh:mm:ss"));
                setResult(RESULT_OK,returnIntent);

                finish();
            }
        });

    }
    public static String getDate(long milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}
