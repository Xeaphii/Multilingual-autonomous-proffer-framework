package androidassignment.crossover.com.androidassignment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

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
                PopupMenu popupMenu = new PopupMenu(context,
                        v);
                popupMenu.getMenuInflater().inflate(R.menu.auction_item_menu,

                        popupMenu.getMenu());
                popupMenu
                        .setOnMenuItemClickListener(new OnMenuItemClickListener() {

                            @Override
                            public boolean onMenuItemClick(MenuItem arg0) {
                                // TODO Auto-generated method stub
                                if (arg0.getItemId() == R.id.action_delete)
                                {
                                    //do something
                                }else {
                                    Intent editItem = new Intent(context,EditAuctionItem.class);
                                    context.startActivity(editItem);

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
