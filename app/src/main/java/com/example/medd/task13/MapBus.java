package com.example.medd.task13;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class MapBus extends ActionBarActivity {

    private static String TAG = MainActivity.class.getSimpleName();
    private TextView busPatterns;
    private final static String PAAC_KEY = "vTN6gUhHv44pUCJamB7rXpJGW";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapus);

        // search patterns of a route
        busPatterns = (TextView) findViewById(R.id.busPatterns);
        String url = "http://realtime.portauthority.org/bustime/api/v2/getpatterns";
        String fullUrl = Http.urlString(url, "key", PAAC_KEY, "rt", "61A", "format", "json");
        searchPatterns(fullUrl);
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


    private void searchPatterns(String url) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {

                    // Parsing json object response
                    // response will be a json object
                    JSONObject btrsp = response.getJSONObject("bustime-response");
                    JSONArray ptr = btrsp.getJSONArray("ptr");
                    ArrayList<Route> ptrs = new ArrayList<Route>();
                    for (int i = 0; i < ptr.length(); i++) {
                        JSONObject routeJson = ptr.getJSONObject(i);
                        Route route = new Route();
                        route.setPid(routeJson.getString("pid"));
                        route.setLn(routeJson.getString("ln"));
                        route.setRtdir(routeJson.getString("rtdir"));
                        JSONArray points = routeJson.getJSONArray("pt");
                        ArrayList<Point> pts = new ArrayList<Point>();
                        for (int j = 0; j < points.length(); j++) {
                            JSONObject ptObj = points.getJSONObject(j);
                            Point pt = new Point();
                            pt.setSeq(ptObj.getString("seq"));
                            pt.setLat(ptObj.getString("lat"));
                            pt.setLon(ptObj.getString("lon"));
                            pt.setTyp(ptObj.getString("typ"));
                            pt.setPdist(ptObj.getString("pdist"));
                            System.out.println(pt);
                            pts.add(pt);
                        }
                        route.setPts(pts);
                        ptrs.add(route);
                    }

                    busPatterns.append(ptrs.toString());


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
