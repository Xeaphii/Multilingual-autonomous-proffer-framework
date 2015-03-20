package androidassignment.crossover.com.androidassignment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * Created by Sunny on 3/19/2015.
 */
public class EditAuctionItem extends Activity {
    private static String ITEM_ID = "item_id";
    EditText Title, Category, Description, MinBid, Location, EndDate;
    Button AddItem;
    ImageView ModifyPhoto;
    DatabaseHelper db;
    Bitmap ImageResult = null;
    String key = "0";
    private static final int CAMERA_REQUEST = 1888;
    AuctionItem auctionItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_auction_item);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            key = extras.getString(ITEM_ID);
        }
        db = new DatabaseHelper(getApplicationContext());
        Title = (EditText) findViewById(R.id.et_title);
        Category = (EditText) findViewById(R.id.et_category);
        Description = (EditText) findViewById(R.id.et_descp);
        MinBid = (EditText) findViewById(R.id.et_min_bid);
        Location = (EditText) findViewById(R.id.et_location);
        EndDate = (EditText) findViewById(R.id.et_date_time);
        EndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDateTimePicker();
            }
        });

        EndDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ShowDateTimePicker();
                }
            }
        });
        AddItem = (Button) findViewById(R.id.btn_add_item);
        ModifyPhoto = (ImageView) findViewById(R.id.iv_add_photo);
        ModifyPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
        auctionItem = new AuctionItem();
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
                int result = db.UpdateAuctionItem(key, item);
                if (result == -1)
                    Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_LONG).show();
                else {
                    if (ImageResult != null)
                        ImageManup.saveToInternalSorage(ImageResult, getApplicationContext(), Integer.toString(result));
                    Toast.makeText(getApplicationContext(), "Item updated successfully.", Toast.LENGTH_LONG).show();
                    finish();
                }

            }
        });
        new GetAuctionItem().execute();

    }

    class GetAuctionItem extends AsyncTask<Void, Integer, String> {


        protected void onPreExecute() {
        }

        protected String doInBackground(Void... arg0) {
            auctionItem = db.GetAuctionItem(key);

            return "";
        }


        protected void onPostExecute(String result) {
            Title.setText(auctionItem.getTitle());
            Category.setText(auctionItem.getCategory());
            Description.setText(auctionItem.getDescription());
            MinBid.setText(auctionItem.getMinBid());
            Location.setText(auctionItem.getLocation());
            EndDate.setText(auctionItem.getEndDate());
            ModifyPhoto.setImageBitmap(ImageManup.loadImageFromStorage(Integer.toString(
                    auctionItem.getImageLoc()), getApplicationContext()));

        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            ImageResult = ImageManup.getResizedBitmap((Bitmap) data.getExtras().get("data"), 120, 120);
            ModifyPhoto.setImageBitmap(ImageResult);
        }
    }

    private void ShowDateTimePicker() {
        final View dialogView = View.inflate(EditAuctionItem.this, R.layout.date_time_picker, null);

        final AlertDialog alertDialog = new AlertDialog.Builder(EditAuctionItem.this).create();

        dialogView.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
                TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);


                String DateTime = datePicker.getYear() + "-" +
                        (datePicker.getMonth() + 1) + "-" +
                        datePicker.getDayOfMonth() + " " +
                        timePicker.getCurrentHour() + ":" +
                        timePicker.getCurrentMinute() + ":00";


                EndDate.setText(DateTime);
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(dialogView);
        alertDialog.show();
    }
}
