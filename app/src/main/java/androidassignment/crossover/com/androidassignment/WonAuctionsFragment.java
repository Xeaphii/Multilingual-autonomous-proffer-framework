package androidassignment.crossover.com.androidassignment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class WonAuctionsFragment extends Fragment {
    DatabaseHelper db;
    private List<AuctionItem> WonAuctions;
    SoldAuctionsAdapter adapter;
    ListView lv;
    CacheData cacheData;
    SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        WonAuctions = new ArrayList<AuctionItem>();
        prefs = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        db = new DatabaseHelper(getActivity());
        cacheData = new CacheData();
        View rootView = inflater.inflate(R.layout.fragment_won_auctions, container, false);
        lv = (ListView) rootView.findViewById(R.id.listView);
        new GetWonAuctions().execute();
        return rootView;
    }

    class GetWonAuctions extends AsyncTask<Void, Integer, String> {


        protected void onPreExecute() {
        }

        protected String doInBackground(Void... arg0) {
            if (cacheData.isWonDataPresent()) {
                WonAuctions = cacheData.getWonAuctions();
            } else {

                WonAuctions = db.getWonAuctions(db.GetUserId(prefs.getString("user_name", "")));
                cacheData.setWonAuctions(WonAuctions);
            }
            return "";
        }


        protected void onPostExecute(String result) {
            adapter = new SoldAuctionsAdapter(getActivity(), WonAuctions);
            lv.setAdapter(adapter);

            /*lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent placeBid = new Intent(getActivity(), PlaceBid.class);
                    startActivity(placeBid);
                }
            });*/
        }
    }

    class RefreshItems extends AsyncTask<Void, Integer, String> {


        protected void onPreExecute() {
        }

        protected String doInBackground(Void... arg0) {
            WonAuctions.clear();
            WonAuctions.addAll(db.getWonAuctions(db.GetUserId(prefs.getString("user_name", ""))));
            return "";
        }


        protected void onPostExecute(String result) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        new RefreshItems().execute();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
