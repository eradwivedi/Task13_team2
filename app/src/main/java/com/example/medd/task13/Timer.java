package com.example.medd.task13;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Lei on 5/9/15.
 */
public class Timer {

    public Timer() {
    }


    public static String diff(String time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd hh:mm");
        long rst = -1;
        try {
            Date future = df.parse(time);
            Date now = new Date();
            long diff = future.getTime() - now.getTime();
            rst = (long)Math.floor(diff / (1000 * 60.0));
        } catch(Exception e) {
            e.printStackTrace();
        }

        return Long.toString(rst);
    }

    public static String diff(String tmstmp, String prdtm) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd hh:mm");
        long rst = -1;
        try {
            Date future = df.parse(prdtm);
            Date now = df.parse(tmstmp);
            long diff = future.getTime() - now.getTime();
            rst = (long) Math.floor(diff / (1000 * 60.0));
        } catch(Exception e) {
            e.printStackTrace();
        }

        return Long.toString(rst);
    }
}
