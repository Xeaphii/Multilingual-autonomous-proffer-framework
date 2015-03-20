package androidassignment.crossover.com.androidassignment;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sunny on 3/20/2015.
 */
public class AuctionedHistory extends Activity {
    private final static String AUCTION_ITEM_ID = "auctioned_item_id";
    private final static String AUCTION_ITEM_TITLE = "auctioned_item_title";
    TextView ItemTitle;
    String ItemId = "";
    DatabaseHelper db;
    ListView lv;
    HistoryAuctionedItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auctioned_history);
        Bundle extras = getIntent().getExtras();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        db = new DatabaseHelper(getApplicationContext());
        ItemTitle = (TextView) findViewById(R.id.auction_item_title);
        if (extras != null) {
            ItemId = extras.getString(AUCTION_ITEM_ID);
            ItemTitle.setText(extras.getString(AUCTION_ITEM_TITLE));
        }
        new GetHistory().execute();
    }

    class GetHistory extends AsyncTask<Void, Integer, String> {


        protected void onPreExecute() {
        }

        protected String doInBackground(Void... arg0) {
            List<AuctionHistoryPojo> History = new ArrayList<AuctionHistoryPojo>();
            History = db.GetHistoryBids(ItemId);
             adapter = new HistoryAuctionedItemAdapter(getApplicationContext()
                    , History);
             lv = (ListView) findViewById(R.id.listView);

            return "";
        }


        protected void onPostExecute(String result) {
            lv.setAdapter(adapter);
        }
    }


}
