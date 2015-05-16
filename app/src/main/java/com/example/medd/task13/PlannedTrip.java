package com.example.medd.task13;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.app.PendingIntent.getActivity;


public class PlannedTrip extends Activity {
    public static final String KEY_BUSTIME = "bus_time";
    ListView list;
    static TripPlannerRowArrayAdapter rowsadapter;
    static TripPlanner []array;
     private TripPlannerrowitem route;
    private Button back_home;
    ArrayList<GoogleRoute> googleRoutes = new ArrayList<>();
   ArrayList<TripPlannerrowitem> trips=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_planned_trip);

        setContentView(R.layout.activity_planned_trip);
        list=(ListView)findViewById(R.id.list_plantrip);
        back_home=(Button)findViewById(R.id.back_home);
        // Get all the routes from bundle
        Bundle b = getIntent().getExtras();
        final ArrayList<GoogleRoute> routes = (ArrayList<GoogleRoute>) b.getSerializable("routes");

        //ArrayList<HashMap<String, String>> routesToDisplay = getRoutesToDisplay(routes);
//        Log.d("route_details", routesToDisplay.toString());

        trips=getRoutesToDisplay(routes);
        Log.d("inplannedtrip","yes");

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("asd","asd");

        rowsadapter =new TripPlannerRowArrayAdapter(PlannedTrip.this, R.layout.list_row_plan_trip, trips);
        // listView.addHeaderView(header);
        list.setAdapter(rowsadapter);
     back_home.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent i=new Intent(PlannedTrip.this,MainActivity.class);
             startActivity(i);
         }
     });
        // Getting adapter by passing xml data ArrayList
      //  adapter=new LazyAdapter(this, routesToDisplay);
      //  list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TripPlannerrowitem tmp = trips.get(position);
                int route_id=tmp.getRoute_id();
                Log.d("in onItemClick","yes");
                Intent intent = new Intent(PlannedTrip.this, RouteDetails.class);
                intent.putExtra("clickedroute",routes.get(route_id));
                startActivity(intent);
            }
        });
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

    //public ArrayList<HashMap<String, String>> getRoutesToDisplay(ArrayList<GoogleRoute> routes) {
    public ArrayList<TripPlannerrowitem> getRoutesToDisplay(ArrayList<GoogleRoute> routes) {

        // display
       // ArrayList<HashMap<String, String>> routesToDisplay = new ArrayList<HashMap<String, String>>();
        ArrayList<TripPlannerrowitem> arrayl=new ArrayList<>();
        for (int i = 0; i < routes.size(); i++) {
            GoogleRoute googleRoute = routes.get(i);
          //  HashMap<String, String> routeToDisplay = new HashMap<>();
            String arrival_time = googleRoute.getArrival_time();
            String departure_time = googleRoute.getDeparture_time();
            String distance = googleRoute.getDistance();
            ArrayList<String> stepNames = googleRoute.getStepNames();

            String stepDescription = stepNames.toString();
            String detail = departure_time + " - " + arrival_time + " " ;//+ distance;
          //  routeToDisplay.put(KEY_BUSTIME, detail);
           // routesToDisplay.add(routeToDisplay);

            TripPlannerrowitem r=new TripPlannerrowitem();
             r.setTime(detail);
            r.setDescription(stepDescription);
            r.setDistance(distance);
            r.setRoute_id(i);
            arrayl.add(r);
        }
        return arrayl;
        //return routesToDisplay;
    }

//
//    public ArrayList<HashMap<String, String>> getStepsToDisplay(GoogleRoute route) {
//        String origin = route.getStart_address();
//        String dest = route.getEnd_address();
//        ArrayList<GoogleStep> steps = route.getSteps();
//        ArrayList<String> stepsDetail = new ArrayList<>();
//        for (int i = 0; i < steps.size(); i++) {
//            GoogleStep step = steps.get(i);
//            String traval_mode = step.getTravel_mode();
//            String html_instruction = step.get
//        }
//    }


}
