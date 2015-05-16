package com.example.medd.task13;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Lei on 5/11/15.
 */
public class GoogleRoute implements Serializable {
    // same as leg
    private String arrival_time;
    private String departure_time;
    private String distance;
    private String duration;
    private String end_address;
    private Point  end_location;
    private String start_address;
    private Point start_location;
    private ArrayList<GoogleStep> steps;

    private String overview_polyline;

    public GoogleRoute(){}

    public GoogleRoute(String overview_polyline, String arrival_time, String departure_time, String distance, String duration,
                       String end_address, Point end_location, String start_address,
                       Point start_location, ArrayList<GoogleStep> steps){
        this.overview_polyline = overview_polyline;
        this.arrival_time = arrival_time;
        this.departure_time = departure_time;
        this.distance = distance;
        this.duration = duration;
        this.end_location = end_location;
        this.end_address = end_address;
        this.start_address = start_address;
        this.start_location = start_location;
        this.steps = steps;
        Log.d("GoogleRoute constructor", this.toString());
    }

    public String getOverview_polyline() {
        return overview_polyline;
    }

    public String getStart_address() {
        return start_address;
    }

    public String getEnd_address() {
        return end_address;
    }

    public String getArrival_time() {
        return arrival_time;
    }

    public String getDeparture_time() {
        return departure_time;
    }

    public String getDistance() {
        return distance;
    }

    public ArrayList<GoogleStep> getSteps() {
        return steps;
    }

    public ArrayList<String> getStepNames() {
        ArrayList<String> stepNames = new ArrayList<>();
        for (int i = 0; i < this.getSteps().size(); i++) {
            GoogleStep step = this.getSteps().get(i);
            stepNames.add(step.getStepName());
        }
        return stepNames;
    }

    public String toString() {
        return departure_time + " - " + arrival_time + " " + distance + " " + getStepNames().toString();
    }

    public void setArrival_Time(String arrival_time) {
        this.arrival_time = arrival_time;
    }

    public void setDeparture_time(String departure_time) {
        this.departure_time = departure_time;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setEnd_address(String end_address) {
        this.end_address = end_address;
    }

    public void setEnd_location(Point end_location) {
        this.end_location = end_location;
    }

}
