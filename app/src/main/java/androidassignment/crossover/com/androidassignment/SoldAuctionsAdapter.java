package androidassignment.crossover.com.androidassignment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sunny on 3/20/2015.
 */
public class SoldAuctionsAdapter extends ArrayAdapter<AuctionItem> {
    Context context;
    private List<AuctionItem> ActiveAuctions;
    private static String ITEM_ID = "item_id";
    DatabaseHelper db;
    private final static String AUCTION_ITEM_ID = "auctioned_item_id";
    private final static String AUCTION_ITEM_TITLE = "auctioned_item_title";

    public SoldAuctionsAdapter(Activity context, List<AuctionItem> activeAuctions) {
        super(context, R.layout.auction_item_row_sold, activeAuctions);
        // TODO Auto-generated constructor stub
        ActiveAuctions = new ArrayList<AuctionItem>();
        this.ActiveAuctions = activeAuctions;
        db = new DatabaseHelper(context);
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View single_row = inflater.inflate(R.layout.auction_item_row_sold, null,
                true);
        TextView AuctionTitle = (TextView) single_row.findViewById(R.id.auction_title);
        TextView AuctionPrice = (TextView) single_row.findViewById(R.id.auction_item_price);
        TextView AuctionCountBids = (TextView) single_row.findViewById(R.id.auction_items_count);
        TextView AuctionTimeDate = (TextView) single_row.findViewById(R.id.auction_items_date_time);
        TextView AuctionLocation = (TextView) single_row.findViewById(R.id.auction_items_location);
        ImageView imageView = (ImageView) single_row.findViewById(R.id.iv_auction_image);


        AuctionTitle.setText(ActiveAuctions.get(position).getTitle());
        AuctionTimeDate.setText(ActiveAuctions.get(position).getEndDate());
        AuctionLocation.setText(ActiveAuctions.get(position).getLocation());
        AuctionPrice.setText(Integer.toString(ActiveAuctions.get(position).getMaxBid()));
        AuctionCountBids.setText(Integer.toString(ActiveAuctions.get(position).getCount()));
        imageView.setImageBitmap(ImageManup.loadImageFromStorage(Integer.toString(ActiveAuctions.get(position).getImageLoc()), context));

        View overflow = single_row.findViewById(R.id.album_overflow);
        overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context,
                        v);
                popupMenu.getMenuInflater().inflate(R.menu.sold_auction_item_menu,

                        popupMenu.getMenu());
                popupMenu
                        .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                            @Override
                            public boolean onMenuItemClick(MenuItem arg0) {
                                // TODO Auto-generated method stub
                                if (arg0.getItemId() == R.id.action_history) {
                                    //do something
                                    Intent HistoryIntent = new Intent(context, AuctionedHistory.class);
                                    HistoryIntent.putExtra(AUCTION_ITEM_ID, Integer.toString(
                                            ActiveAuctions.get(position).getImageLoc()));
                                    HistoryIntent.putExtra(AUCTION_ITEM_TITLE,ActiveAuctions.get(position).getTitle());
                                    context.startActivity(HistoryIntent);
                                }

                                return false;
                            }
                        });
                popupMenu.show();


            }
        });
        return single_row;
    }
}
