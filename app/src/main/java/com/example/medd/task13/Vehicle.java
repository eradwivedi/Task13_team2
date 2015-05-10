package com.example.medd.task13;
/**
 * Created by Lei on 5/8/15.
 */
public class Vehicle {
    private String vid; // bus #
    private String rt; // route #
    private String rtdir; // route direction
    private String stpnm; // current bus stop name
    private String des; // destination stop name
    private String dstp; // linear distance(feet) left to the stop
    private String tmleft; // predicted arrival/departure time (minutes left)

    private String tmstmp; // last position updated
    private double lat; // latitude position
    private double lon; // longtitude position
    private double hdg; // heading degree
    private String plist; // Linear distance in feet that the vehicle has traveled into the pattern currently being executed
    private boolean dly; // The value is “true” if the vehicle is delayed.

    public Vehicle() {
        this.dly = false;
    }

    public void setRt(String rt) {
        this.rt = rt;
    }

    public void setDstp(String dstp) {
        this.dstp = dstp;
    }

    public void setTmleft(String tmleft) {
        this.tmleft = tmleft;
    }

    public void setTmstmp(String tmstmp) {
        this.tmstmp = tmstmp;
    }

    public void setLat(String lat) {
        this.lat = Double.parseDouble(lat);
    }

    public void setLon(String lon) {
        this.lon = Double.parseDouble(lon);
    }

    public void setHdg(String hdg) {
        this.hdg = Double.parseDouble(hdg);
    }

    public void setPlist(String plist) {
        this.plist =  plist;
    }

    public void setDly(String dly) {
        this.dly = (dly == "true");
    }

    public String getVid() { return vid; }

    public String getRt() { return rt; }

    public String getRtdir() { return rtdir; }

    public String getDes() { return des; }

    public String getDstp() {
        return dstp;
    }

    public String getTmleft() {
        return tmleft;
    }

    public String toString() {
        return rt + " " + tmleft;
    }
}

