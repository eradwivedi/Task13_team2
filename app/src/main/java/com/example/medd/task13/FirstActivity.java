package com.example.medd.task13;

/**
 * Created by Era Dwivedi
 */
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FirstActivity extends Activity implements AdapterView.OnItemSelectedListener {
   private String busnoselected;
    private String stopselected;
    HashMap<String,Integer> stop_bus_selected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        final Spinner bus_no_spinner = (Spinner) findViewById(R.id.spinner_bus_no);
        // Spinner click listener
        List<String> bus_no = new ArrayList<String>();
        bus_no.add("Enter stop");
        bus_no.add("71B");
        bus_no.add("71A");
        bus_no.add("61A");
        bus_no.add("61B");
        bus_no.add("1B");
        bus_no.add("70C");
        bus_no.add("77");
        bus_no.add("82");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter_bus = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,bus_no);
        // Drop down layout style - list view with radio button
        dataAdapter_bus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        bus_no_spinner.setAdapter(dataAdapter_bus);

        bus_no_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                       busnoselected=bus_no_spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // Spinner Drop down elements

        final Spinner bound_spinner = (Spinner) findViewById(R.id.spinner_bound);
        // Spinner click listener
        bus_no_spinner.setOnItemSelectedListener(this);
        // Spinner Drop down elements
        List<String> bound = new ArrayList<String>();
        bound.add("Enter direction");
        bound.add("Inbound");
        bound.add("Outbound");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter_bound = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,bound);
        // Drop down layout style - list view with radio button
        dataAdapter_bound.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        bound_spinner.setAdapter(dataAdapter_bound);


        final Spinner stop_spinner = (Spinner) findViewById(R.id.spinner_stop);
        // Spinner click listener
        stop_spinner.setOnItemSelectedListener(this);
        // Spinner Drop down elements
         stop_bus_selected=fetchStops(busnoselected);

        List<String> stop = new ArrayList<String>();
        stop.add("Select Stop");
        for(String s:stop_bus_selected.keySet())
        {
            stop.add(s);
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter_stop = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,stop);
        // Drop down layout style - list view with radio button
        dataAdapter_stop.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        stop_spinner.setAdapter(dataAdapter_stop);

        stop_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stopselected=stop_spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button check=(Button)findViewById(R.id.go);
        check.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle b=new Bundle();
                int stop_id=stop_bus_selected.get(stopselected);
                b.putInt("Stop_id",stop_id);
                Intent intent=new Intent(FirstActivity.this,BusTimings.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    private HashMap fetchStops(String busno)
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

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
