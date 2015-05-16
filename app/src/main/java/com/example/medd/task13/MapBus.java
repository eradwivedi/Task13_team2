package com.example.medd.task13;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.graphics.Color;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MapBus extends ActionBarActivity{

    private static String TAG = MainActivity.class.getSimpleName();
//    private Fragment busPatterns;
    private final static String PAAC_KEY = "vTN6gUhHv44pUCJamB7rXpJGW";
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapus);

        Bundle b = getIntent().getExtras();
        String string = b.getString("toPassMap");
        String[] strArray = string.split(" ");

        // search patterns of a route
        //busPatterns = (TextView) findViewById(R.id.busPatterns);

        String stopUrl = "http://realtime.portauthority.org/bustime/api/v2/getstops";
        String stopFulUrl = Http.urlString(stopUrl, "key", PAAC_KEY, "rt", strArray[0], "dir", strArray[3], "format", "json");
        searchPatterns(stopFulUrl);

        String vehicleUrl = "http://realtime.portauthority.org/bustime/api/v2/getvehicles";
        String vehivleFullUrl = Http.urlString( vehicleUrl, "key", PAAC_KEY, "rt", strArray[0], "format", "json");
        disPlayVehicle(vehivleFullUrl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mapus, menu);
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

    private void disPlayVehicle(String url){

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    ArrayList<Vehicle> vList = new ArrayList<Vehicle>();
                    JSONObject btrsp = response.getJSONObject("bustime-response");
                    JSONArray vehicles = btrsp.getJSONArray("vehicle");
                    for (int i = 0; i < vehicles.length(); i++) {
                        JSONObject vehicleObject = vehicles.getJSONObject(i);
                        Vehicle newVehicle = new Vehicle();
                        newVehicle.setVid(vehicleObject.getString("vid"));
                        newVehicle.setRt(vehicleObject.getString("rt"));
                        newVehicle.setLat(vehicleObject.getString("lat"));
                        newVehicle.setLon(vehicleObject.getString("lon"));
                        newVehicle.setDes(vehicleObject.getString("des"));
                        if(vehicleObject.getString("dly").equals(true)){
                            newVehicle.setDly("True");
                        }
                        vList.add(newVehicle);
                    }

                    if(googleMap == null){
                        googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                                R.id.map)).getMap();
                    }


                    if(googleMap != null){

                        for(int i = 0; i < vList.size(); i++){
                            Vehicle stop = vList.get(i);
                            LatLng newPt = new LatLng(stop.getLat(),stop.getLon());
                            googleMap.addMarker(new MarkerOptions()
                                    .title("Bus: "+stop.getVid())
                                    .snippet( "Route: "+ stop.getRt() + " to "+stop.getDes())
                                    .position(newPt))
                                    .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.bus_curr_32));
                        }
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
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Adding request to request queue
        AppControllerSearchTests.getInstance().addToRequestQueue(jsonObjReq);

    }

    private void searchPatterns(String url) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    ArrayList<Stop> stopList = new ArrayList<Stop>();
                    JSONObject btrsp = response.getJSONObject("bustime-response");
                    JSONArray stops = btrsp.getJSONArray("stops");
                    for (int i = 0; i < stops.length(); i++) {
                        JSONObject stop = stops.getJSONObject(i);
                        Stop newStop = new Stop();
                        newStop.setStpnm(stop.getString("stpnm"));
                        newStop.setLat(stop.getString("lat"));
                        newStop.setLon(stop.getString("lon"));
                        stopList.add(newStop);
                    }
                    googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                            R.id.map)).getMap();
                    if(googleMap == null){

                    } else{
//                        List<LatLng> decodedPath = PolyUtil.decode("qdzuFf_|fNDHCBOVSfAsBjSQxBCpBLlMT`OBf@J|@`AlF`@zB]HgAZ}CdAkGtBpDnFtBbD`BfC~CtEfBjCGHt@rw@");
//                        googleMap.addPolyline(new PolylineOptions().addAll(decodedPath).width(30)
//                                .color(Color.MAGENTA)
//                                .geodesic(true));
                        LatLng cmu = new LatLng(40.4411801, -79.9428294);
                        googleMap.setMyLocationEnabled(true);
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cmu, 15));
                        googleMap.setOnCameraChangeListener(new OnCameraChangeListener(){
                            @Override
                            public void onCameraChange(CameraPosition arg0) {
                                if(arg0.zoom < 14){
                                        googleMap.animateCamera(CameraUpdateFactory.zoomTo(14));
                                }
                            }
                        });


                        for(int i = 0; i < stopList.size(); i++){
                            Stop stop = stopList.get(i);
                            LatLng newPt = new LatLng(stop.getLat(),stop.getLon());
                            googleMap.addMarker(new MarkerOptions()
                                    .title(stop.getStpnm())
                                    .position(newPt))
                            .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.bus_24));
                        }
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
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Adding request to request queue
        AppControllerSearchTests.getInstance().addToRequestQueue(jsonObjReq);
    }
}
