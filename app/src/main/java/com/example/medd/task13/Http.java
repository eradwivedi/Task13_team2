package com.example.medd.task13;

/**
 * Created by Lei on 5/8/15.
 */
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import 	java.net.URLEncoder;
import android.util.Log;
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
