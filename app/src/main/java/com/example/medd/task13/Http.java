package com.example.medd.task13;

/**
 * Created by Lei on 5/8/15.
 */

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

public class Http {

    static String response = null;
    public final static int GET = 1;
    public final static int POST = 2;

    private static String TAG = MainActivity.class.getSimpleName();

    public Http() {

    }

    public static String urlEncode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String urlString(String url, Object... params) {
        StringBuilder sb = new StringBuilder();
        sb.append(url);

        for (int i = 0; i < params.length; i++) {
            if (i % 2 == 0) {
                if (i == 0) {
                    sb.append("?");
                } else {
                    sb.append("&");
                }
                sb.append(urlEncode(params[i].toString()));

                if (i + 1 < params.length) {
                    sb.append("=");
                    if (params[i + 1] != null) {
                        sb.append(urlEncode(params[i + 1].toString()));
                    }
                }
            }
        }
        Log.d("TEST", sb.toString());
        return sb.toString();
    }


    public static String urlString(String base, HashMap<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(base + "?");
        for (String paramKey: params.keySet()) {
            sb.append(urlEncode(paramKey) + "=" + urlEncode(params.get(paramKey)) + "&");
        }
        sb.deleteCharAt(sb.length() - 1);
        String rst = sb.toString();
        Log.d("TEST", rst);
        return rst;
    }
}
