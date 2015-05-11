package com.example.medd.task13;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;


public class PlannedTrip extends ActionBarActivity {
    private static String TAG = MainActivity.class.getSimpleName();

    private TextView txtShowAllRts;
    private TextView txtShowStops;
    private TextView txtSearchByStop;

    private final static String PAAC_KEY = "vTN6gUhHv44pUCJamB7rXpJGW";
    private SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd hh:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planned_trip);

        // fetch all the rts(routes)
        txtShowAllRts = (TextView) findViewById(R.id.txtShowAllRts);
        String rtUrl = "http://realtime.portauthority.org/bustime/api/v2/getroutes";
        String rtFullUrl = Http.urlString(rtUrl, "key", PAAC_KEY, "format", "json");
        showAllRts(rtFullUrl);

        // show all stops of a rt
        txtShowStops = (TextView) findViewById(R.id.txtShowStops);
        String stopUrl = "http://realtime.portauthority.org/bustime/api/v2/getstops";
        String stopFulUrl = Http.urlString(stopUrl, "key", PAAC_KEY, "rt", "61A", "dir", "INBOUND", "format", "json");
        showStops(stopFulUrl);

        // search upcoming buses by stop#
        txtSearchByStop = (TextView) findViewById(R.id.txtSearchByStop);
        String url = "http://realtime.portauthority.org/bustime/api/v2/getpredictions";
        String fullUrl = Http.urlString(url, "key", PAAC_KEY, "stpid", 7116, "format", "json");
        searchByStop(fullUrl);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_planned_trip, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showAllRts(String url) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {

                    // Parsing json object response
                    // response will be a json object
                    ArrayList<String> rts = new ArrayList<String>();
                    JSONObject btrsp = response.getJSONObject("bustime-response");
                    JSONArray routes = btrsp.getJSONArray("routes");
                    for (int i = 0; i < routes.length(); i++) {
                        JSONObject route = routes.getJSONObject(i);
                        rts.add(route.getString("rt"));
                    }

                    txtShowAllRts.append(rts.toString());


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Adding request to request queue
        AppControllerSearchTests.getInstance().addToRequestQueue(jsonObjReq);
    }

    private void showStops(String url) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {

                    // Parsing json object response
                    // response will be a json object
                    HashMap<Integer, String> stopMap = new HashMap<>();
                    JSONObject btrsp = response.getJSONObject("bustime-response");
                    JSONArray stops = btrsp.getJSONArray("stops");
                    for (int i = 0; i < stops.length(); i++) {
                        JSONObject stop = stops.getJSONObject(i);
                        stopMap.put(Integer.parseInt(stop.getString("stpid")),
                                    stop.getString("stpnm"));
                    }

                    txtShowStops.append(stopMap.toString());


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Adding request to request queue
        AppControllerSearchTests.getInstance().addToRequestQueue(jsonObjReq);
    }

    private void searchByStop(String url) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {

                    // Parsing json object response
                    // response will be a json object
                    ArrayList<Vehicle> comingBuses = new ArrayList<Vehicle>();
                    JSONObject btrsp = response.getJSONObject("bustime-response");
                    JSONArray prd = btrsp.getJSONArray("prd");
                    for (int i = 0; i < prd.length(); i++) {
                        Vehicle bus = new Vehicle();
                        JSONObject busObj = prd.getJSONObject(i);
                        bus.setRt(busObj.getString("rt"));
                        bus.setDstp(busObj.getString("dstp"));

                        String prdtmStr = busObj.getString("prdtm");
                        String tmstmpStr = busObj.getString("tmstmp");
                        bus.setTmleft(Timer.diff(tmstmpStr, prdtmStr));
                        comingBuses.add(bus);
                    }

                    txtSearchByStop.append(comingBuses.toString());


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Adding request to request queue
        AppControllerSearchTests.getInstance().addToRequestQueue(jsonObjReq);
    }
}
