package com.example.medd.task13;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by Lei on 5/12/15.
 */
public class GoogleStep implements Serializable{
    private String distance;
    private String duration;
    private Point end_location;
    private String html_instructions;
    private String polyline;
    private Point start_location;

    // only for bus in "transit_details"
    private String travel_mode; // "TRANSIT"/"Walking"
    private Stop arrival_stop; // lat, lon, name
    private String arrival_time;
    private Stop departure_stop; // lat, lon, name
    private String departure_time;
    private String line; // "71B"
    private String transit_type; // "BUS"
    private String num_stops; // 6

    public GoogleStep(String distance, String duration, Point end_location, String html_instructions,
                      String polyline, Point start_location, String travel_mode) {
        this.distance = distance;
        this.duration = duration;
        this.end_location = end_location;
        this.html_instructions = html_instructions;
        this.polyline = polyline;
        this.start_location = start_location;
        this.travel_mode = travel_mode;
        Log.d("GoogleStepStep.java", polyline);
    }

    public String getDeparture_time() {
        return departure_time;
    }

    public String getArrival_time() {
        return arrival_time;
    }

    public String getDeparture_stop() {
        return departure_stop.getStpnm();
    }

    public String getArrival_stop() {
        return arrival_stop.getStpnm();
    }


    public String getTravel_mode() {
        return travel_mode;
    }

    public String getHtml_instructions() {
        return html_instructions;
    }

    public String getDistance() {
        return distance;
    }

    public String getDuration() {
        return duration;
    }

    public String getPolyline() {
        return polyline;
    }

    public String getStepName() {
        if (travel_mode.equals("TRANSIT")) {
            Log.d("getStepName", line);
            return line;
        } else {
            Log.d("getStepName", travel_mode);
            return travel_mode;
        }
    }

    public void setArrival_stop(Stop arrival_stop) {
        this.arrival_stop = arrival_stop;
    }

    public void setArrival_time(String arrival_time) {
        this.arrival_time = arrival_time;
    }

    public void setDeparture_stop(Stop departure_stop) {
        this.departure_stop = departure_stop;
    }

    public void setDeparture_time(String departure_time) {
        this.departure_time = departure_time;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public void setTransit_type(String transit_type) {
        this.transit_type = transit_type;
    }

    public void setNum_stops(String num_stops) {
        this.num_stops = num_stops;
    }

}
/*
GoogleStep googleStep = new GoogleStep(arrival_stop, transit_arrival_time,
                                    departure_stop, transit_departure_time, line, transit_type, num_stops);
 */