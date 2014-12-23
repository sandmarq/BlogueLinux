package bloguelinux.sandmarq.ca.bloguelinux;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by sandrine on 2014-12-23.
 */
public class MyAdapterShowList extends ArrayAdapter<ShowsList> {
    public MyAdapterShowList(Context context, ShowsList values[]) {
        super(context, R.layout.shows_list, values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater theInflater = LayoutInflater.from(getContext());

        View theView = theInflater.inflate(R.layout.shows_list, parent, false);

        ShowsList selectedShow = getItem(position);

        TextView theTextView1 = (TextView) theView.findViewById(R.id.tvTitle);
        TextView theTextView2 = (TextView) theView.findViewById(R.id.tvDescription);
        //TextView theTextView3 = (TextView) theView.findViewById(R.id.tvLienMp3);
        //TextView theTextView4 = (TextView) theView.findViewById(R.id.tvLienOgg);

        theTextView1.setText(selectedShow.getTitre());
        theTextView2.setText(selectedShow.getDescription());
        //theTextView3.setText(selectedShow.getLienMp3());
        //theTextView4.setText(selectedShow.getLienOgg());

        return theView;
    }
}
