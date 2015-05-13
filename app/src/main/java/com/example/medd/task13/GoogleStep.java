package com.example.medd.task13;

/**
 * Created by Lei on 5/12/15.
 */
public class GoogleStep {
    private String distance;
    private String duration;
    private Point end_location;
    private String html_instructions;
    private String polyline;
    private Point start_location;

    // only for bus in "transit_details"
    private String travel_mode; // "TRANSIT"/"Walking"
    private Point arrival_stop; // lat, lon, name
    private String arrival_time;
    private Stop departure_stop; // lat, lon, name
    private String departure_time;
    private String line; // "71B"
    private String transit_type; // "BUS"
    private String num_stops; // 6


}
