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

public class ActiveAuctionsFragment extends Fragment {


    DatabaseHelper db;
    private List<AuctionItem> ActiveAuctions;
    AuctionsAdapter adapter;
    ListView lv;
    CacheData cacheData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ActiveAuctions = new ArrayList<AuctionItem>();
        db = new DatabaseHelper(getActivity());
        cacheData = new CacheData();
        View rootView = inflater.inflate(R.layout.fragment_active_auctions, container, false);
        lv = (ListView) rootView.findViewById(R.id.listView);

        new GetActiveAuctions().execute();
        return rootView;
    }

    class GetActiveAuctions extends AsyncTask<Void, Integer, String> {


        protected void onPreExecute() {
        }

        protected String doInBackground(Void... arg0) {
            if (cacheData.isActiveDataPresent()) {
                ActiveAuctions = cacheData.getActiveAuctions();
            } else {
                ActiveAuctions = db.getActiveAuctions();
                cacheData.setActiveAuctions(ActiveAuctions);
            }
            return "";
        }


        protected void onPostExecute(String result) {
            adapter = new AuctionsAdapter(getActivity(), ActiveAuctions);
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
            ActiveAuctions.clear();
            ActiveAuctions.addAll(db.getActiveAuctions());
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
