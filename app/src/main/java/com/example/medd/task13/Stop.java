package com.example.medd.task13;

import java.io.Serializable;

/**
 * Created by Lei on 5/10/15.
 */
public class Stop implements Serializable{
    private int stpid;
    private String stpnm;
    private double lat;
    private double lon;

    public Stop() {}

    public Stop(String stpnm, String lat, String lon) {
        this.stpnm = stpnm;
        this.lat = Double.parseDouble(lat);
        this.lon = Double.parseDouble(lon);
    }

    public void setStpid(String stpid) {
        this.stpid = Integer.parseInt(stpid);
    }

    public void setStpnm(String stpnm) {
        this.stpnm = stpnm;
    }

    public void setLat(String lat) {
        this.lat = Double.parseDouble(lat);
    }

    public void setLon(String lon) {
        this.lon = Double.parseDouble(lon);
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getStpnm() {
        return stpnm;
    }

    public int getStpid() {
        return stpid;
    }

    public String toString() {
        return stpid + ": " + stpnm;
    }
}
