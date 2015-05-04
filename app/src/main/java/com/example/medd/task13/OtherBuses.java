package com.example.medd.task13;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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

        ArrayList<HashMap<String, String>> otherbusesToDisplay = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> otherbus=new HashMap<String, String>();
        HashMap<String, String> otherbus1=new HashMap<String, String>();

        //ArrayList<BustimingsDataobject> bustimings=new ArrayList<BustimingsDataobject>();
        otherbus.put(KEY_OTHERBUSES,"58");
        otherbus1.put(KEY_OTHERBUSES,"77");
        otherbusesToDisplay.add(otherbus);
        otherbusesToDisplay.add(otherbus1);
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
