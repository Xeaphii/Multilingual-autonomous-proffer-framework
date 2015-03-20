package androidassignment.crossover.com.androidassignment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Sunny on 3/18/2015.
 */
public class AddAuctionItem extends Activity {
    EditText Title, Category, Description, MinBid, Location, EndDate;
    Button AddItem;
    private static final int CAMERA_REQUEST = 1888;
    ImageView AddPhoto;
    DatabaseHelper db;
    Bitmap ImageResult = null;

    SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_auction);
        Initializatoin();
    }

    private void Initializatoin() {
        prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        db = new DatabaseHelper(getApplicationContext());
        Title = (EditText) findViewById(R.id.et_title);
        Category = (EditText) findViewById(R.id.et_category);
        Description = (EditText) findViewById(R.id.et_descp);
        MinBid = (EditText) findViewById(R.id.et_min_bid);
        Location = (EditText) findViewById(R.id.et_location);
        EndDate = (EditText) findViewById(R.id.et_date_time);
        AddItem = (Button) findViewById(R.id.btn_add_item);
        AddPhoto = (ImageView) findViewById(R.id.iv_add_photo);
        EndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View dialogView = View.inflate(getApplicationContext(), R.layout.date_time_picker, null);
                final AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();

                dialogView.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
                        TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);

                        Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                                datePicker.getMonth(),
                                datePicker.getDayOfMonth(),
                                timePicker.getCurrentHour(),
                                timePicker.getCurrentMinute());

                        EndDate.setText(getDate(calendar.getTimeInMillis(),"yyyy-MM-dd hh:mm:ss"));
                        alertDialog.dismiss();
                    }});
                alertDialog.setView(dialogView);
                alertDialog.show();
            }
        });
        AddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuctionItem item = new AuctionItem();
                item.setTitle(Title.getText().toString());
                item.setCategory(Category.getText().toString());
                item.setDescription(Description.getText().toString());
                item.setMinBid(MinBid.getText().toString());
                item.setLocation(Location.getText().toString());
                item.setEndDate(EndDate.getText().toString());
                item.setUserId(db.GetUserId(prefs.getString("user_name", "")));
                int result = db.InsertAuctionItem(item);
                if (result == -1)
                    Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_LONG).show();
                else {
                    ImageManup.saveToInternalSorage(ImageResult, getApplicationContext(), Integer.toString(result));
                    Toast.makeText(getApplicationContext(), "Item posted successfully.", Toast.LENGTH_LONG).show();
                    finish();
                }

            }
        });
        AddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            ImageResult = ImageManup.getResizedBitmap((Bitmap) data.getExtras().get("data"), 120, 120);
            AddPhoto.setImageBitmap(ImageResult);
        }
    }
    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}
