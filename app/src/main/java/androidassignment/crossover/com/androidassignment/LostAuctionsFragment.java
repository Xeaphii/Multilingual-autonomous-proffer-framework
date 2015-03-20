package androidassignment.crossover.com.androidassignment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class LostAuctionsFragment extends Fragment {
    DatabaseHelper db;
    private List<AuctionItem> LostAuctions;
    SoldAuctionsAdapter adapter;
    ListView lv;
    CacheData cacheData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LostAuctions = new ArrayList<AuctionItem>();
        db = new DatabaseHelper(getActivity());
        cacheData = new CacheData();
        View rootView = inflater.inflate(R.layout.fragment_lost_auctions, container, false);
        lv = (ListView) rootView.findViewById(R.id.listView);
        new GetLostAuctions().execute();
        return rootView;
    }
    class GetLostAuctions extends AsyncTask<Void, Integer, String> {


        protected void onPreExecute() {
        }

        protected String doInBackground(Void... arg0) {
            if (cacheData.isLostDataPresent()) {
                LostAuctions = cacheData.getLostAuctions();
            } else {
                LostAuctions = db.getLostAuctions();
                cacheData.setLostAuctions(LostAuctions);
            }
            return "";
        }


        protected void onPostExecute(String result) {
            adapter = new SoldAuctionsAdapter(getActivity(), LostAuctions);
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
            LostAuctions.clear();
           LostAuctions.addAll(db.getLostAuctions());
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
