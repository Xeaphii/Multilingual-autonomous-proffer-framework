package androidassignment.crossover.com.androidassignment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private MenuItem refreshMenuItem = null;

    Menu menu = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        WonAuctions = new ArrayList<AuctionItem>();
        prefs = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        db = new DatabaseHelper(getActivity());
        cacheData = new CacheData();
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_won_auctions, container, false);
        lv = (ListView) rootView.findViewById(R.id.listView);
        new GetWonAuctions().execute();
        return rootView;
    }

    class GetWonAuctions extends AsyncTask<Void, Integer, String> {


        protected void onPreExecute() {
            if(refreshMenuItem != null){
                refreshMenuItem.setActionView(R.layout.action_progressbar);

                refreshMenuItem.expandActionView();
            }
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

            if(refreshMenuItem != null){
                refreshMenuItem.collapseActionView();
                // remove the progress bar view
                refreshMenuItem.setActionView(null);
            }
        }
    }

    class RefreshItems extends AsyncTask<Void, Integer, String> {


        protected void onPreExecute() {
            if(refreshMenuItem != null){
                refreshMenuItem.setActionView(R.layout.action_progressbar);

                refreshMenuItem.expandActionView();
            }
        }

        protected String doInBackground(Void... arg0) {
            WonAuctions.clear();
            WonAuctions.addAll(db.getWonAuctions(db.GetUserId(prefs.getString("user_name", ""))));
            return "";
        }


        protected void onPostExecute(String result) {
            adapter.notifyDataSetChanged();
            if(refreshMenuItem != null){
                refreshMenuItem.collapseActionView();
                // remove the progress bar view
                refreshMenuItem.setActionView(null);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        new RefreshItems().execute();
    }
    class RefreshItemsCustom extends AsyncTask<Void, Integer, String> {


        protected void onPreExecute() {
            refreshMenuItem.setActionView(R.layout.action_progressbar);

            refreshMenuItem.expandActionView();
        }

        protected String doInBackground(Void... arg0) {
            WonAuctions.clear();
            WonAuctions.addAll(db.getWonAuctions(db.GetUserId(prefs.getString("user_name", ""))));
            cacheData.setWonAuctions(WonAuctions);
            return "";
        }


        protected void onPostExecute(String result) {
            adapter.notifyDataSetChanged();
            refreshMenuItem.collapseActionView();
            // remove the progress bar view
            refreshMenuItem.setActionView(null);
/*
            Toast.makeText(getActivity(),"Won",Toast.LENGTH_SHORT).show();
*/
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_refresh:
                refreshMenuItem = item;
                new RefreshItemsCustom().execute();
                // search action
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        this.menu = menu;
        refreshMenuItem = menu.findItem(R.id.action_refresh);
        super.onCreateOptionsMenu(menu, inflater);

    }
}
