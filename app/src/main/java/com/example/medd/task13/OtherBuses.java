package com.example.medd.task13;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;


public class OtherBuses extends ActionBarActivity {
    ListView list;
    OtherBusesAdapter adapter;
    public static final String KEY_OTHERBUSES="other_buses";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_buses);

        Bundle b = getIntent().getExtras();
        ArrayList<String> slist = b.getStringArrayList("otherbus");

        ArrayList<HashMap<String, String>> otherbusesToDisplay = new ArrayList<HashMap<String, String>>();
            Log.d("list", ""+slist.size());

        for(String s:slist){
            String[] strArray = s.split(" ");
            HashMap<String, String> otherbus=new HashMap<String, String>();
            otherbus.put(KEY_OTHERBUSES,strArray[1]+"      "+strArray[2] + " Minutes");
            otherbusesToDisplay.add(otherbus);
        }


        list=(ListView)findViewById(R.id.list);
        // Getting adapter by passing xml data ArrayList

        adapter=new OtherBusesAdapter(this, otherbusesToDisplay);
        list.setAdapter(adapter);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_other_buses, menu);
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
