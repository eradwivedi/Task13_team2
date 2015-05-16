package com.example.medd.task13;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class TripPlanner extends Activity implements AdapterView.OnItemSelectedListener {
    private final static String GOOGLE_KEY = "AIzaSyAT1IGf9QrEESKP_nBd23Dv9CydaT9FWqw";

    private String originSelected = "Carnegie Mellon University";
    private String destSelected = "University of Pittsburgh";
    private boolean departureSelected = true; // true -> departure, false -> arrival
    private String dateSelected;
    private String timeSelected;
    private String time = "now";
    private String preferenceSelected = "fewer_transfers"; // less_walking/fewer_transfers
    private String modeSelected = "bus"; // bus/subway/train/tram/rail
    EditText origin;
    EditText dest;
    private ArrayList<GoogleRoute> googleRoutes = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_planner);
        /*origin=(EditText)findViewById(R.id.start_address_t);
        dest=(EditText)findViewById(R.id.end_address_t);
        Log.d("checkedittext",origin.getText().toString());
        Log.d("checkedittext",dest.getText().toString());
        originSelected=origin.getText().toString();
        destSelected=dest.getText().toString();
*/

        final Spinner mode_spinner = (Spinner) findViewById(R.id.spinner_mode);
        // Spinner click listener
        mode_spinner.setOnItemSelectedListener(this);
        // Spinner Drop down elements
        List<String> mode = new ArrayList<String>();
        mode.add("Mode");
        mode.add("bus");
        mode.add("train");
        mode.add("subway");
        mode.add("tram");
        mode.add("rail");
         // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter_mode = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,mode);
        // Drop down layout style - list view with radio button
        dataAdapter_mode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        mode_spinner.setAdapter(dataAdapter_mode);

        final Spinner route_spinner = (Spinner) findViewById(R.id.spinner_route_desc);
        // Spinner click listener
        mode_spinner.setOnItemSelectedListener(this);
        // Spinner Drop down elements
        List<String> route_desc_list = new ArrayList<String>();
        route_desc_list.add("fewer transfers");
        route_desc_list.add("less walking");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter_route = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,route_desc_list);
        // Drop down layout style - list view with radio button
        dataAdapter_route.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        route_spinner.setAdapter(dataAdapter_route);
        route_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                preferenceSelected = route_spinner.getSelectedItem().toString().replace(" ", "_");
                Log.d("preference", preferenceSelected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final Button pick_date=(Button)findViewById(R.id.datepicker);
        pick_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");

            }
        });


        final Button pick_time=(Button)findViewById(R.id.timepicker);
        pick_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment1 = new TimePickerFragment();
                newFragment1.show(getFragmentManager(), "timePicker");
            }
        });

        Button plan=(Button)findViewById(R.id.plan);
        plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText origin=(EditText)findViewById(R.id.start_address_t);
                final EditText dest=(EditText)findViewById(R.id.end_address_t);
                Log.d("checkedittext",origin.getText().toString());
                Log.d("checkedittext",dest.getText().toString());
                originSelected=origin.getText().toString();
                destSelected=dest.getText().toString();

                // search routes using google map
                String base = "https://maps.googleapis.com/maps/api/directions/json";
                HashMap<String, String> params = new HashMap();
                params.put("key", GOOGLE_KEY);
                params.put("mode", "transit");
                params.put("origin", originSelected);
                params.put("destination", destSelected);
                if (departureSelected == true) {
                    params.put("departure_time", time);
                } else {
                    params.put("arrival_time", time);
                }
                params.put("transit_routing_preference", preferenceSelected);
                params.put("transit_mode", modeSelected);
                params.put("alternatives", "true");

                final String googleUrl = Http.urlString(base, params);

                //==============================================
                // fetch Json
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                        googleUrl, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(AppControllerSearchTests.TAG, response.toString());

                        try {
                            JSONArray routes = response.getJSONArray("routes");
                            for (int i = 0; i < routes.length(); i++) {

                                JSONObject leg = routes.getJSONObject(i).getJSONArray("legs").getJSONObject(0); // only one leg
                                String overview_polyline = routes.getJSONObject(i).getJSONObject("overview_polyline").getString("points");
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

                                Log.d("start", "here");
                                GoogleRoute googleRoute = new GoogleRoute(overview_polyline,arrival_time, departure_time, distance,
                                        duration, end_address, end_location, start_address, start_location, googleSteps);
                                googleRoutes.add(googleRoute);
                                Log.d("hello", googleRoutes.toString());

                                // go to next page
                                Bundle b = new Bundle();
                                b.putSerializable("routes", googleRoutes);
                                Intent intent = new Intent(TripPlanner.this, PlannedTrip.class);
                                intent.putExtras(b);
                                startActivity(intent);

                            }
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
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trip_planner, menu);
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

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
