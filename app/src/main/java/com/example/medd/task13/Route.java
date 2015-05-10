package com.example.medd.task13;

/**
 * Created by Lei on 5/9/15.
 */
import java.util.ArrayList;

public class Route {
    private int pid;
    private double ln;
    private String rtdir;
    private ArrayList<Point> pts;

    public Route() {}

    public void setPid(String pid) {
        this.pid = Integer.parseInt(pid);
    }

    public void setLn(String ln) {
        this.ln = Double.parseDouble(ln);
    }

    public void setRtdir(String rtdir) {
        this.rtdir = rtdir;
    }

    public void setPts(ArrayList<Point> pts) {
        this.pts = pts;
    }

    public String toString() {
        return "dir: " + rtdir + pts.toString();
    }
}
