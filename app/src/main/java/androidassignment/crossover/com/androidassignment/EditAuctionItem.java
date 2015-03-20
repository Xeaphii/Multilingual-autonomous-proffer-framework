package androidassignment.crossover.com.androidassignment;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by Sunny on 3/19/2015.
 */
public class EditAuctionItem extends Activity {
    private static String ITEM_ID = "item_id";
    EditText Title, Category, Description, MinBid, Location, EndDate;
    Button AddItem;
    ImageView ModifyPhoto;
    DatabaseHelper db;
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
        AddItem = (Button) findViewById(R.id.btn_add_item);
        ModifyPhoto = (ImageView) findViewById(R.id.iv_add_photo);
        auctionItem = new AuctionItem();
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
}
