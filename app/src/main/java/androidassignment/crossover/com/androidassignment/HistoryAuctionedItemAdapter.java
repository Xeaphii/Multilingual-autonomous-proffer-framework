package androidassignment.crossover.com.androidassignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sunny on 3/20/2015.
 */
public class HistoryAuctionedItemAdapter extends ArrayAdapter<AuctionHistoryPojo> {
    private List<AuctionHistoryPojo> History;
    Context context;

    public HistoryAuctionedItemAdapter(Context context,List<AuctionHistoryPojo> history) {
        super(context, R.layout.history_row, history);
        // TODO Auto-generated constructor stub
        History = new ArrayList<AuctionHistoryPojo>();
        this.History = history;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View single_row = inflater.inflate(R.layout.history_row, null,
                true);
        TextView UserName = (TextView) single_row.findViewById(R.id.tv_user_name);
        TextView BidAmount = (TextView) single_row.findViewById(R.id.tv_bid_amount);
        UserName.setText(History.get(position).getUserName());
        BidAmount.setText(History.get(position).getBidAmount());
        return single_row;
    }
}
