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

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;


public class PlannedTrip extends ActionBarActivity {
    private static String TAG = MainActivity.class.getSimpleName();

    private TextView txtShowAllRts;
    private TextView txtShowStops;
    private TextView txtSearchByStop;
    ArrayList<String> test = new ArrayList<>();
    JSONObject jsonFetched;

    public interface VolleyCallback{
        void onSuccess(JSONObject result);
    }

    VolleyCallback callbackForGoogle = new VolleyCallback() {
        @Override
        public void onSuccess(JSONObject result) {
            jsonFetched = result;
            Log.d("CALLBACK", jsonFetched.toString());
            ArrayList<GoogleRoute> googleRoutes = new ArrayList<>();
            try {
                JSONArray routes = jsonFetched.getJSONArray("routes");
                for (int i = 0; i < routes.length(); i++) {
                    JSONObject leg = routes.getJSONObject(i).getJSONArray("legs").getJSONObject(0); // only one leg
                    String arrival_time = leg.getJSONObject("arrival_time").getString("text");
                    String departure_time = leg.getJSONObject("departure_time").getString("text");
                    String distance = leg.getJSONObject("distance").getString("text");
                    String duration = leg.getJSONObject("duration").getString("text");
                    String end_address = leg.getString("end_address");
                    Point end_location = new Point(leg.getJSONObject("end_location").getString("lat"),
                            leg.getJSONObject("end_location").getString("lng"));
                    String start_address = leg.getString("start_address");
                    Point start_location = new Point(leg.getJSONObject("start_location").getString("lat"),
                            leg.getJSONObject("end_location").getString("lng"));

                    ArrayList<GoogleStep> googleSteps = new ArrayList<>();
                    JSONArray steps = leg.getJSONArray("steps");
                    for (int j = 0; j < steps.length(); j++) {
                        JSONObject step = steps.getJSONObject(j);
                        String step_distance = step.getJSONObject("distance").getString("text");
                        String step_duration = step.getJSONObject("duration").getString("text");
                        Point step_end_location = new Point(step.getJSONObject("end_location").getString("lat"),
                                step.getJSONObject("end_location").getString("lng"));
                        String html_instructions = step.getString("html_instructions");
                        String step_polyline = step.getJSONObject("polyline").getString("points");
                        Point step_start_location = new Point(step.getJSONObject("start_location").getString("lat"),
                                step.getJSONObject("start_location").getString("lng"));
                        String travel_mode = step.getString("travel_mode");
                        Log.d("travel_mode", travel_mode);

                        GoogleStep googleStep = new GoogleStep(step_distance, step_duration, step_end_location,
                                html_instructions, step_polyline, step_start_location, travel_mode);

                        if (travel_mode.equals("TRANSIT")) {
                            //Log.d("hi", "there");
                            JSONObject details = step.getJSONObject("transit_details");
                            Stop arrival_stop = new Stop(details.getJSONObject("arrival_stop").getString("name"),
                                    details.getJSONObject("arrival_stop").getJSONObject("location").getString("lat"),
                                    details.getJSONObject("arrival_stop").getJSONObject("location").getString("lng"));
                            String transit_arrival_time = details.getJSONObject("arrival_time").getString("text");
                            Stop departure_stop = new Stop(details.getJSONObject("departure_stop").getString("name"),
                                    details.getJSONObject("departure_stop").getJSONObject("location").getString("lat"),
                                    details.getJSONObject("departure_stop").getJSONObject("location").getString("lng"));
                            String transit_departure_time = details.getJSONObject("arrival_time").getString("text");
                            String line = details.getJSONObject("line").getString("short_name");
                            String transit_type = details.getJSONObject("line").getJSONObject("vehicle").getString("type"); // "BUS"
                            String num_stops = details.getString("num_stops");
                            //Log.d("arrival_stop", arrival_stop.toString());

                            googleStep.setArrival_stop(arrival_stop);
                            googleStep.setArrival_time(transit_arrival_time);
                            googleStep.setDeparture_stop(departure_stop);
                            googleStep.setDeparture_time(transit_departure_time);
                            googleStep.setLine(line);
                            googleStep.setTransit_type(transit_type);
                            googleStep.setNum_stops(num_stops);
                        }

                        googleSteps.add(googleStep);
                    }

                    GoogleRoute googleRoute = new GoogleRoute(arrival_time, departure_time, distance,
                            duration, end_address, end_location, start_address, start_location, googleSteps);
                    googleRoutes.add(googleRoute);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),
                        "Error: " + e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        }
    };

    private final static String PAAC_KEY = "vTN6gUhHv44pUCJamB7rXpJGW";
    private final static String GOOGLE_KEY = "AIzaSyAT1IGf9QrEESKP_nBd23Dv9CydaT9FWqw";
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

        // search routes using google map
        String base = "https://maps.googleapis.com/maps/api/directions/json";
        HashMap<String, String> params = new HashMap();
        params.put("key", GOOGLE_KEY);
        params.put("mode", "transit");
        params.put("origin", "Carnegie Mellon University");
        params.put("destination", "University of Pittsburgh");
        params.put("departure_time", "now");
        params.put("transit_routing_preference", "less_walking");
        params.put("transit_mode", "bus");
        params.put("alternatives", "true");

        String googleUrl = Http.urlString(base, params);
        //SearchRouteByGoogle(googleUrl);

        fetchJson(googleUrl);

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

                    test.add("hello world");
                    txtShowAllRts.append(rts.toString());
                    Log.d("bus no: ", rts.toString());
                    txtShowAllRts.append(test.get(0));

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
                    Log.d("bus stops for 61B", stopMap.toString());


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

    private JSONObject fetchJson(String url) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                //jsonFetched = response;
                callbackForGoogle.onSuccess(response);
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
        return jsonFetched;
    }
}
