package com.example.medd.task13;

/**
 * Created by Era Dwivedi
 */
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class FirstActivity extends Activity implements AdapterView.OnItemSelectedListener {

    HashMap<String,Integer> stop_bus_selected = new HashMap();
    public static final String KEY_BUSTIME="bus_time";

    private final static String PAAC_KEY = "vTN6gUhHv44pUCJamB7rXpJGW";
    private SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd hh:mm");
    ArrayList<String> bus_no;
    ArrayList<String> stop_name;
    Spinner bus_no_spinner;
    Spinner bound_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        stop_bus_selected = new HashMap<>();

        bus_no_spinner = (Spinner) findViewById(R.id.spinner_bus_no);

        bus_no = new ArrayList<String>();
        bus_no.add("Enter stop");



        // get bus route numbers
        String rtbase = "http://realtime.portauthority.org/bustime/api/v2/getroutes";
        String rtUrl = Http.urlString(rtbase, "key", PAAC_KEY, "format", "json");

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                rtUrl, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("getroutes", response.toString());

                try {

                    // Parsing json object response
                    // response will be a json object
                    JSONObject btrsp = response.getJSONObject("bustime-response");
                    JSONArray routes = btrsp.getJSONArray("routes");
                    for (int i = 0; i < routes.length(); i++) {
                        JSONObject route = routes.getJSONObject(i);
                        bus_no.add(route.getString("rt"));
                    }

                    Log.d("bus no: ", bus_no.toString());

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
                VolleyLog.d(AppControllerSearchTests.TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Adding request to request queue
        AppControllerSearchTests.getInstance().addToRequestQueue(jsonObjReq);









        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter_bus = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,bus_no);
        // Drop down layout style - list view with radio button
        dataAdapter_bus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        bus_no_spinner.setAdapter(dataAdapter_bus);
        bus_no_spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements

        bound_spinner = (Spinner) findViewById(R.id.spinner_bound);
        // Spinner click listener

        // Spinner Drop down elements
        List<String> bound = new ArrayList<String>();
        bound.add("Enter direction");
        bound.add("INBOUND");
        bound.add("OUTBOUND");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter_bound = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,bound);
        // Drop down layout style - list view with radio button
        dataAdapter_bound.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        bound_spinner.setAdapter(dataAdapter_bound);
        bound_spinner.setOnItemSelectedListener(this);

        stop_name = new ArrayList<String>();
        final Spinner stop_spinner = (Spinner) findViewById(R.id.spinner_stop);
        stop_name.add("Select Stop");
        ArrayAdapter<String> dataAdapter_stop = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stop_name);
        // Drop down layout style - list view with radio button
        dataAdapter_stop.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        stop_spinner.setAdapter(dataAdapter_stop);


        Button check=(Button)findViewById(R.id.go);
        check.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                String url = "http://realtime.portauthority.org/bustime/api/v2/getpredictions";
                String fullUrl = Http.urlString(url, "key", PAAC_KEY, "stpid", stop_bus_selected.get(stop_spinner.getSelectedItem().toString()), "format", "json");


                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                        fullUrl, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


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

                            ArrayList<String> bustimingsToDisplay = new ArrayList<String>();
                            for (Vehicle v : comingBuses) {
                                bustimingsToDisplay.add(bus_no_spinner.getSelectedItem().toString()+" "+v.getRt() +" "+ v.getTmleft()+ " "+bound_spinner.getSelectedItem().toString());
                            }


                            Bundle b = new Bundle();

                            b.putStringArrayList("list",bustimingsToDisplay);
                            Intent intent = new Intent(FirstActivity.this, BusTimings.class);
                            intent.putExtras(b);
                            startActivity(intent);
                            Log.d("number in ", "" + bustimingsToDisplay.size());

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
                        // VolleyLog.d(TAG, "Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                // Adding request to request queue
                AppControllerSearchTests.getInstance().addToRequestQueue(jsonObjReq);



            }
        });


    }

    private HashMap fetchStops(String busno, String bound)
    {   HashMap<String,Integer> stops=new HashMap<>();
        //
        return stops;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first, menu);
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



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        if(bus_no_spinner.getSelectedItem().toString().equals("Enter stop") || bound_spinner.getSelectedItem().toString().equals("Enter direction")){

        }else{

            Toast.makeText(getApplicationContext(),
                    "updated",
                    Toast.LENGTH_SHORT).show();
            stop_name.clear();
            String stopbase = "http://realtime.portauthority.org/bustime/api/v2/getstops";
            String stopUrl = Http.urlString(stopbase, "key", PAAC_KEY, "rt", bus_no_spinner.getSelectedItem().toString(), "dir", bound_spinner.getSelectedItem().toString(), "format", "json");
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    stopUrl, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    Log.d("getstops", response.toString());

                    try {

                        // Parsing json object response
                        // response will be a json object

                        JSONObject btrsp = response.getJSONObject("bustime-response");
                        JSONArray stops = btrsp.getJSONArray("stops");
                        for (int i = 0; i < stops.length(); i++) {
                            JSONObject stop = stops.getJSONObject(i);
                            stop_bus_selected.put(stop.getString("stpnm"),
                                    Integer.parseInt(stop.getString("stpid")));
                        }

                        // Spinner Drop down elements
                        Log.d("outside", stop_bus_selected.toString());

                        for(String s:stop_bus_selected.keySet())
                        {
                            stop_name.add(s);
                            Log.d("one stop", s);
                        }
                        Collections.sort(stop_name);

                        Log.d("bus stops for "+bus_no_spinner.getSelectedItem().toString(), stop_name.toString());


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
                    VolleyLog.d(AppControllerSearchTests.TAG, "Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            // Adding request to request queue
            AppControllerSearchTests.getInstance().addToRequestQueue(jsonObjReq);
            //=================================================================================



            Log.d("stop names: ", stop_name.toString());

        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        stop_name.add("n");
    }
}
