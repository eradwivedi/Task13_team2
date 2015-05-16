package com.example.medd.task13;

import java.io.Serializable;

/**
 * Created by Lei on 5/9/15.
 */
public class Point implements Serializable{
    private int seq;
    private double lat;
    private double lon;
    private String typ;
    //private int stpid;
    //private String stpnm;
    private double pdist;

    public Point(){};

    public Point(String lat, String lon) {
        this.lat = Double.parseDouble(lat);
        this.lon = Double.parseDouble(lon);
    }



    public void setSeq(String seq) {
        this.seq = Integer.parseInt(seq);
    }

    public void setLat(String lat) {
        this.lat = Double.parseDouble(lat);
    }

    public void setLon(String lon) {
        this.lon = Double.parseDouble(lon);
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

//    public void setStpid(String stpid) {
//        this.stpid = Integer.parseInt(stpid);
//    }
//
//    public void setStpnm(String stpnm) {
//        this.stpnm = stpnm;
//    }

    public void setPdist(String pdist) {
        this.pdist = Double.parseDouble(pdist);
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String toString() {
        return "[" + lat + ", " + lon + "]";
    }
}
