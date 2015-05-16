package com.example.medd.task13;
/**
 * Created by Era on 4/13/2015.
 */
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.medd.task13.R;

public class LazyAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    //public ImageLoader imageLoader;

    public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

       // imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.bus_timimgs_list_row, null);

        TextView name = (TextView)vi.findViewById(R.id.bus_clicked); // title
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.time_img); // thumb image

        HashMap<String, String> timing = new HashMap<String, String>();
        timing = data.get(position);

        // Setting all values in listview

        name.setText(timing.get(BusTimings.KEY_BUSTIME));
        thumb_image.setImageResource(R.drawable.clock_50);
       // imageLoader.DisplayImage(account.get(CustomizedListView.KEY_THUMB_URL), thumb_image);
        return vi;
    }
}