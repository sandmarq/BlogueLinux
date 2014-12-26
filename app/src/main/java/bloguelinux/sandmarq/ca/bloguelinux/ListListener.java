package bloguelinux.sandmarq.ca.bloguelinux;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;

import java.util.List;

/**
 * Created by sandrine on 2014-12-26.
 */
public class ListListener implements AdapterView.OnItemClickListener {
        // Our listener will contain a reference to the list of RSS Items
        // List item's reference
        List<ShowsList> listItems;
        // And a reference to a calling activity
        // Calling activity reference
        Activity activity;
        /** We will set those references in our constructor.*/
        public ListListener(List<ShowsList> aListItems, Activity anActivity) {
            listItems = aListItems;
            activity  = anActivity;
        }

        /** Start a browser with url from the rss item.*/
    public void onItemClick(AdapterView parent, View view, int pos, long id) {
        // We create an Intent which is going to display data
        Intent i = new Intent(Intent.ACTION_VIEW);
        // We have to set data for our new Intent
        i.setData(Uri.parse(listItems.get(pos).getLienOgg()));
        // And start activity with our Intent
        activity.startActivity(i);
    }
}
