package androidassignment.crossover.com.androidassignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class LostAuctionsFragment extends Fragment {
    String color_names[] = {"red", "green", "blue", "yellow", "pink", "brown"};
    Integer image_id[] = {R.drawable.ic_launcher, R.drawable.ic_launcher,
            R.drawable.ic_launcher, R.drawable.ic_launcher,
            R.drawable.ic_launcher, R.drawable.ic_launcher};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_lost_auctions, container, false);
        CustomActiveAuctionlistadapter adapter = new CustomActiveAuctionlistadapter(getActivity(), image_id, color_names);
        ListView lv = (ListView) rootView.findViewById(R.id.listView);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent placeBid = new Intent(getActivity(), PlaceBid.class);
                startActivity(placeBid);
            }
        });
        return rootView;
    }

}
