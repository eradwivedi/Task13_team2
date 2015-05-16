package com.example.medd.task13;

/**
 * Created by Era on 5/14/2015.
 */

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;


import java.util.ArrayList;

import static android.content.Intent.getIntent;


public class TripPlannerRowArrayAdapter extends ArrayAdapter<TripPlannerrowitem> {
    Context context;
    public ArrayList<TripPlannerrowitem> data;
    int layoutResourceId;
    // ResultItem  data[] = null;

    public TripPlannerRowArrayAdapter(Context context, int layoutResourceId, ArrayList<TripPlannerrowitem> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ResultHolder holder = null;
        final LinearLayout container = (LinearLayout) row;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ResultHolder();
            //      holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.time = (TextView) row.findViewById(R.id.time);
            holder.distance = (TextView) row.findViewById(R.id.distance);
            holder.description= (TextView) row.findViewById(R.id.description);
            row.setTag(holder);
        } else {
            holder = (ResultHolder) row.getTag();
        }

        TripPlannerrowitem tmp = data.get(position);
        //TestResultItem tmp=data.get(position);
        holder.time.setText(tmp.getTime());
        holder.distance.setText(tmp.getDistance());
        holder.description.setText(tmp.getDescription());

        return row;
    }

    static class ResultHolder {
        TextView time,distance,description;//,num_rating;
    }

    public void updateAdapter(ArrayList<TripPlannerrowitem> newlist) {
        //data.clear();
        //data.addAll(newlist);
        data = newlist;
        this.notifyDataSetChanged();
    }
}