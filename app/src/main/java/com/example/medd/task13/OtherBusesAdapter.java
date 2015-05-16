package com.example.medd.task13;

/**
 * Created by Era Dwivedi
 */
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.medd.task13.R;

import static android.support.v4.app.ActivityCompat.startActivity;

public class OtherBusesAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    //public ImageLoader imageLoader;

    public OtherBusesAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
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
            vi = inflater.inflate(R.layout.other_buses_list_row, null);

        TextView name = (TextView)vi.findViewById(R.id.other_bus_text); // title
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.bus_other); // thumb image

        HashMap<String, String> other_buses = new HashMap<String, String>();
        other_buses = data.get(position);

        // Setting all values in listview

        name.setText(other_buses.get(OtherBuses.KEY_OTHERBUSES));
        thumb_image.setImageResource(R.drawable.bus_50_red);
        // imageLoader.DisplayImage(account.get(CustomizedListView.KEY_THUMB_URL), thumb_image);
        return vi;
    }
}