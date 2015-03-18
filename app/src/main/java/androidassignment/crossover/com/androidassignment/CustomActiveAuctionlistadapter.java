package androidassignment.crossover.com.androidassignment;

import android.app.Activity;
import android.content.Context;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by Sunny on 3/18/2015.
 */
public class CustomActiveAuctionlistadapter extends ArrayAdapter<String> {
    String[] color_names;
    Integer[] image_id;
    Context context;

    public CustomActiveAuctionlistadapter(Activity context, Integer[] image_id, String[] text) {
        super(context, R.layout.auctions_row, text);
        // TODO Auto-generated constructor stub
        this.color_names = text;
        this.image_id = image_id;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View single_row = inflater.inflate(R.layout.auctions_row, null,
                true);
       /* TextView textView = (TextView) single_row.findViewById(R.id.textView);
        ImageView imageView = (ImageView) single_row.findViewById(R.id.imageView);
        textView.setText(color_names[position]);
        imageView.setImageResource(image_id[position]);*/
        View overflow = single_row.findViewById(R.id.album_overflow);
        overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, v) {
                    @Override
                    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.album_overflow_rename:

                                return true;


                            default:
                                return super.onMenuItemSelected(menu, item);
                        }
                    }
                };

                popupMenu.inflate(R.menu.auction_item_menu);


                popupMenu.show();
            }
        });
        return single_row;
    }

}
