package com.example.medd.task13;

import java.util.ArrayList;

/**
 * Created by Lei on 5/11/15.
 */
public class GoogleRoute {
    // same as leg
    private String arrival_time;
    private String departure_time;
    private String distance;
    private String duration;
    private String end_address;
    private Point end_location;
    private String start_address;
    private Point start_location;
    private ArrayList<GoogleStep> steps;

    public GoogleRoute(){}

    public GoogleRoute(String arrival_time, String departure_time, String distance, String duration,
                       String end_address, Point end_location, String start_address,
                       Point start_location, ArrayList<GoogleStep> steps){
        this.arrival_time = arrival_time;
        this.departure_time = departure_time;
        this.distance = distance;
        this.duration = duration;
        this.end_location = end_location;
        this.end_address = end_address;
        this.start_address = start_address;
        this.start_location = start_location;
        this.steps = steps;
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
