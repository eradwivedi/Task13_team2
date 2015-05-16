package com.example.medd.task13;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Lei on 5/13/15.
 */
public class RouteDetails extends Activity {
    ListView list;
    LazyAdapter adapter;
    private GoogleRoute selected_route;
    private TextView start_add,end_add,distance,steps,start_time,end_time;
    private String polyline;
    private Button back_home,map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_planned_trip);

        setContentView(R.layout.activity_route_details);
//        list=(ListView)findViewById(R.id.list);
        selected_route=(GoogleRoute)getIntent().getSerializableExtra("clickedroute");
        polyline = selected_route.getOverview_polyline();
        Log.d("route_details", "Hello");
        back_home=(Button)findViewById(R.id.back_home1);
        map=(Button)findViewById(R.id.to_map);
        start_add=(TextView)findViewById(R.id.startaddress_detailed);
        end_add=(TextView)findViewById(R.id.endaddress_detailed);
        distance=(TextView)findViewById(R.id.distance_detailed);
        steps=(TextView)findViewById(R.id.steps_detailed);
        start_time=(TextView)findViewById(R.id.starttime_detailed);
        end_time=(TextView)findViewById(R.id.endtime_detailed);
        start_add.setText(selected_route.getStart_address());
        end_add.setText(selected_route.getEnd_address());
        distance.setText(selected_route.getDistance());
        start_time.setText(selected_route.getDeparture_time());
        end_time.setText(selected_route.getArrival_time());
        ArrayList<String> ins=getStepsToDisplay(selected_route);
        String dis="";
        for(int i=0;i<getStepsToDisplay(selected_route).size();i++) {
            dis=dis+"\n"+ins.get(i);
        }
        steps.setText(dis);
        back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(RouteDetails.this,MainActivity.class);
                startActivity(i);
            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("polyline", polyline);
                Intent i=new Intent(RouteDetails.this,RouteMap.class);
                i.putExtras(b);
                startActivity(i);
            }
        });

        // Getting adapter by passing xml data ArrayList
//        adapter=new LazyAdapter(this, routesToDisplay);
//        list.setAdapter(adapter);
    }

    public ArrayList<String> getStepsToDisplay(GoogleRoute route) {
        ArrayList<GoogleStep> steps = route.getSteps();
        ArrayList<String> instructions = new ArrayList<>();
        for (int i = 0; i < steps.size(); i++) {
            GoogleStep step = steps.get(i);
            String polyline = step.getPolyline();
            String traval_mode = step.getTravel_mode();
            String html_instruction = step.getHtml_instructions();
            String step_distance = step.getDistance();
            String step_duration = step.getDuration();
            StringBuilder instructionSB = new StringBuilder();
            instructionSB.append("Step Name : "+step.getStepName() + "\n");
            instructionSB.append("\n");
            instructionSB.append("Details   : "+html_instruction);
            instructionSB.append("\n");
            instructionSB.append("\n");
            if (traval_mode.equals("TRANSIT")) {
                String departureTime = step.getDeparture_time();
                String departureStop = step.getDeparture_stop();
                String arrivalTime = step.getArrival_time();
                String arrivalStop = step.getArrival_stop();
                instructionSB.append("Dept Time : " + departureTime + " " + departureStop);
                instructionSB.append("\n");
                instructionSB.append("\n");
                instructionSB.append("Arrv Time : " + arrivalTime + " " + arrivalStop);
                instructionSB.append("\n");
            } else {
                instructionSB.append("Distance  : "+step_distance+ " " + step_duration);
            }
            instructions.add(instructionSB.toString());
        }

        return instructions;
    }



}
