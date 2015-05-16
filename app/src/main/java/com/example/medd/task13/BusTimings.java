package com.example.medd.task13;

/**
 * Created by Era Dwivedi
 */
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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


public class BusTimings extends ActionBarActivity {
   public static final String KEY_BUSTIME="bus_time";
    private final static String PAAC_KEY = "vTN6gUhHv44pUCJamB7rXpJGW";

    ListView list;
    ArrayList<HashMap<String, String>> bustimingsToDisplay;
    ArrayList<String> toPass;
    String toPassMap = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //checking
        setContentView(R.layout.activity_bus_timings);
        list=(ListView)findViewById(R.id.list);
        bustimingsToDisplay = new ArrayList<HashMap<String, String>>();

        Bundle b = getIntent().getExtras();
        ArrayList<String> slist = b.getStringArrayList("list");
        toPass = new ArrayList<String>();
        if(slist != null && slist.size() > 0){
            toPassMap = ""+slist.get(0);
        }


        for(String s: slist){
            String[] strArray = s.split(" ");
            if(strArray[0].equals(strArray[1])){
                HashMap<String, String> map =new HashMap<String, String>();
                map.put(KEY_BUSTIME, strArray[2]+" Minutes");
                bustimingsToDisplay.add(map);
            }else{
                toPass.add(s);
            }

        }




        // Getting adapter by passing xml data ArrayList
        LazyAdapter adapter=new LazyAdapter(this, bustimingsToDisplay);
        list.setAdapter(adapter);


        Button check=(Button)findViewById(R.id.maps);
        check.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("toPassMap", toPassMap);
                Intent intent=new Intent(BusTimings.this,MapBus.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        Button other=(Button)findViewById(R.id.otherbuses);
        other.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Bundle b = new Bundle();
                b.putStringArrayList("otherbus", toPass);
                Intent intent=new Intent(BusTimings.this,OtherBuses.class);
                intent.putExtras(b);
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
