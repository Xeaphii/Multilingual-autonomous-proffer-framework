package androidassignment.crossover.com.androidassignment;

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

public class ActiveAuctionsFragment extends Fragment {


    DatabaseHelper db;
    private List<AuctionItem> ActiveAuctions;
    AuctionsAdapter adapter;
    ListView lv;
    CacheData cacheData;
    private MenuItem refreshMenuItem = null;

    Menu menu = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ActiveAuctions = new ArrayList<AuctionItem>();



        db = new DatabaseHelper(getActivity());
        cacheData = new CacheData();
        View rootView = inflater.inflate(R.layout.fragment_active_auctions, container, false);
        lv = (ListView) rootView.findViewById(R.id.listView);
        setHasOptionsMenu(true);

        new GetActiveAuctions().execute();
        return rootView;
    }

    class GetActiveAuctions extends AsyncTask<Void, Integer, String> {


        protected void onPreExecute() {
            if(refreshMenuItem != null){
                refreshMenuItem.setActionView(R.layout.action_progressbar);

                refreshMenuItem.expandActionView();
            }
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
            ActiveAuctions.clear();
            ActiveAuctions.addAll(db.getActiveAuctions());
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

    class RefreshItemsCustom extends AsyncTask<Void, Integer, String> {


        protected void onPreExecute() {
            refreshMenuItem.setActionView(R.layout.action_progressbar);

            refreshMenuItem.expandActionView();
        }

        protected String doInBackground(Void... arg0) {
            ActiveAuctions.clear();
            ActiveAuctions.addAll(db.getActiveAuctions());
            cacheData.setActiveAuctions(ActiveAuctions);
            return "";
        }


        protected void onPostExecute(String result) {
            adapter.notifyDataSetChanged();
            refreshMenuItem.collapseActionView();
            // remove the progress bar view
            refreshMenuItem.setActionView(null);
/*
            Toast.makeText(getActivity(), "Active   ", Toast.LENGTH_SHORT).show();
*/

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
