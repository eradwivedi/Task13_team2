package com.example.medd.task13;

/**
 * Created by Era on 4/25/2015.
 */
import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Era on 4/6/2015.
 */
public class AppControllerSearchTests extends Application {
    public static final String TAG=AppControllerSearchTests.class.getSimpleName(); //
    private RequestQueue mRequestQueue;
//    private ImageLoader mImageLoader;
    static String  NAME="name";
    static String PLACE="place";
    static String FEATURES="features";
    static String RATINGNO="ratingNo";
    static String RATINGAVG="ratingAVG";
    static String PRICEOFFERED="priceOffered";
    static String PRICEACTUAL="priceActual";
    static String IMAGEURL="imgURL";
    static String LONGITUDE="PostionLong";
    static String LATITUDE="PostitionLat";
    public static Boolean NeedUpdate=Boolean.TRUE;
  //  public static ArrayList<ResultItem> fetched;


    private static AppControllerSearchTests mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppControllerSearchTests getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

   /* public ImageLoader getImageLoader() {   // Would be used when fetching image.
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }
*/
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    }
