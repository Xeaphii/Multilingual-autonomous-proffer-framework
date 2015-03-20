package androidassignment.crossover.com.androidassignment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Sunny on 3/18/2015.
 */
public class PlaceBid extends Activity {
    private static String ITEM_ID = "item_id";
    EditText Title, Category, Description, AmountBid, Location, EndDate;
    Button placeBid;
    ImageView ModifyPhoto;
    DatabaseHelper db;
    String key = "0";
    AuctionItem auctionItem;
    SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        setContentView(R.layout.auction_bid);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            key = extras.getString(ITEM_ID);
        }
        db = new DatabaseHelper(getApplicationContext());
        Title = (EditText) findViewById(R.id.et_title);
        Category = (EditText) findViewById(R.id.et_category);
        Description = (EditText) findViewById(R.id.et_descp);
        AmountBid = (EditText) findViewById(R.id.et_place_bid);
        Location = (EditText) findViewById(R.id.et_location);
        EndDate = (EditText) findViewById(R.id.et_date_time);
        placeBid = (Button) findViewById(R.id.btn_place_bid);
        placeBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int MaxPrice = db.getMaxBidPrice(Integer.toString(db.GetUserId(prefs.getString("user_name", "0"))));
                if (MaxPrice == 0) {
                    MaxPrice = Integer.parseInt(auctionItem.getMinBid());
                }
                if ((!AmountBid.getText().toString().isEmpty()) &&
                        Integer.parseInt(AmountBid.getText().toString()) > MaxPrice) {
                    db.InsertBid(db.GetUserId(prefs.getString("user_name", "0")),
                            auctionItem.getImageLoc(), AmountBid.getText().toString());
                    Toast.makeText(getApplicationContext(), "Successfully entered", Toast.LENGTH_LONG).show();
                    finish();
                } else
                    Toast.makeText(getApplicationContext(), "Enter more than $" + MaxPrice, Toast.LENGTH_LONG).show();
            }
        });
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
            int price = db.getMaxBidPrice(Integer.toString(auctionItem.getImageLoc()));
            if (price == 0)
                AmountBid.setHint("Min bidding price: $" + auctionItem.getMinBid());
            else
                AmountBid.setHint("Min bidding price: $" + price);
            Location.setText(auctionItem.getLocation());
            EndDate.setText(auctionItem.getEndDate());
            ModifyPhoto.setImageBitmap(ImageManup.loadImageFromStorage(Integer.toString(
                    auctionItem.getImageLoc()), getApplicationContext()));

        }
    }
}
