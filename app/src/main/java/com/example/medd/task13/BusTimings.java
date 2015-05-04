package com.example.medd.task13;

/**
 * Created by Era Dwivedi
 */
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;


public class BusTimings extends ActionBarActivity {
   public static final String KEY_BUSTIME="bus_time";
    ListView list;
    LazyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //checking
        setContentView(R.layout.activity_bus_timings);

        ArrayList<HashMap<String, String>> bustimingsToDisplay = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> bustiming=new HashMap<String, String>();
        //ArrayList<BustimingsDataobject> bustimings=new ArrayList<BustimingsDataobject>();
        bustiming.put(KEY_BUSTIME,"26 minutes");
        bustimingsToDisplay.add(bustiming);
        HashMap<String, String> bustiming1=new HashMap<String, String>();
        //ArrayList<BustimingsDataobject> bustimings=new ArrayList<BustimingsDataobject>();
        bustiming1.put(KEY_BUSTIME,"Approaching");
        bustimingsToDisplay.add(bustiming1);

       /* for(int i=0;i<accountslist.size();i++)
        {
            HashMap<String,String> a=new HashMap<>();
            a.put(KEY_FNAME,accountslist.get(i).getFirstName());
            a.put(KEY_LNAME,accountslist.get(i).getLastName());
            accountsListToDisplay.add(a);
        }
        List<PersonForm> abc=new ArrayList<PersonForm>();
        persondbhandler.open();
        abc.addAll(persondbhandler.getAll());
        Log.d("No of users", String.valueOf(abc.size()));
        for(int i=0;i<abc.size();i++)
        {
            Log.d("Check firstname "+i,abc.get(i).getFirstName());
            Log.d("Check lastname "+i,abc.get(i).getLastName());
        }*/
        list=(ListView)findViewById(R.id.list);
        // Getting adapter by passing xml data ArrayList

        adapter=new LazyAdapter(this, bustimingsToDisplay);
        list.setAdapter(adapter);
        Button check=(Button)findViewById(R.id.maps);
        check.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BusTimings.this,MapBus.class);
                startActivity(intent);
            }
        });
        Button other=(Button)findViewById(R.id.otherbuses);
        other.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BusTimings.this,OtherBuses.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bus_timings, menu);
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
}
