package com.example.medd.task13;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;


public class set_alarm extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);
         EditText phone = (EditText)findViewById(R.id.phone);
        final Spinner alarm_bus_no_spinner = (Spinner) findViewById(R.id.spinner_alarm_bus_no);
        // Spinner click listener
        List<String> bus_no = new ArrayList<String>();
        bus_no.add("Enter stop");
        bus_no.add("1");
        bus_no.add("12");
        bus_no.add("13");
        bus_no.add("14");
        bus_no.add("15");
        bus_no.add("16");
        bus_no.add("17");
        bus_no.add("18");
        bus_no.add("19L");
        bus_no.add("2");
        bus_no.add("20");
        bus_no.add("21");
        bus_no.add("22");
        bus_no.add("24");
        bus_no.add("26");
        bus_no.add("27");
        bus_no.add("28X");
        bus_no.add("29");
        bus_no.add("31");
        bus_no.add("36");
        bus_no.add("38");
        bus_no.add("39");
        bus_no.add("41");
        bus_no.add("48");
        bus_no.add("51");
        bus_no.add("51L");
        bus_no.add("52L");
        bus_no.add("53");
        bus_no.add("53L");
        bus_no.add("54");
        bus_no.add("55");
        bus_no.add("56");
        bus_no.add("57");
        bus_no.add("58");
        bus_no.add("59");
        bus_no.add("6");
        bus_no.add("60");
        bus_no.add("61A");
        bus_no.add("61B");
        bus_no.add("61C");
        bus_no.add("61D");
        bus_no.add("64");
        bus_no.add("65");
        bus_no.add("67");
        bus_no.add("68");
        bus_no.add("69");
        bus_no.add("71");
        bus_no.add("71A");
        bus_no.add("71B");
        bus_no.add("71C");
        bus_no.add("71D");
        bus_no.add("74");
        bus_no.add("75");
        bus_no.add("77");
        bus_no.add("78");
        bus_no.add("79");
        bus_no.add("8");
        bus_no.add("81");
        bus_no.add("82");
        bus_no.add("83");
        bus_no.add("86");
        bus_no.add("87");
        bus_no.add("88");
        bus_no.add("89");
        bus_no.add("91");
        bus_no.add("93");
        bus_no.add("G2");
        bus_no.add("G3");
        bus_no.add("G31");
        bus_no.add("O1");
        bus_no.add("O12");
        bus_no.add("O5");
        bus_no.add("P1");
        bus_no.add("P10");
        bus_no.add("P12");
        bus_no.add("P13");
        bus_no.add("P16");
        bus_no.add("P17");
        bus_no.add("P2");
        bus_no.add("P3");
        bus_no.add("P67");
        bus_no.add("P68");
        bus_no.add("P69");
        bus_no.add("P7");
        bus_no.add("P71");
        bus_no.add("P76");
        bus_no.add("P78");
        bus_no.add("Y1");
        bus_no.add("Y45");
        bus_no.add("Y46");
        bus_no.add("Y47");
        bus_no.add("Y49");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter_bus = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,bus_no);
        // Drop down layout style - list view with radio button
        dataAdapter_bus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
       alarm_bus_no_spinner.setAdapter(dataAdapter_bus);


        final Spinner alarm_stop_no_spinner = (Spinner) findViewById(R.id.spinner_alarm_stop);
        // Spinner click listener
        List<String> stop = new ArrayList<String>();
        stop.add("Enter stop");
        stop.add("5th at negley");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter_stop = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,stop);
        // Drop down layout style - list view with radio button
        dataAdapter_stop.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        alarm_stop_no_spinner.setAdapter(dataAdapter_stop);


        Button ct=(Button)findViewById(R.id.Choose_time);
         ImageView img=(ImageView)findViewById(R.id.home);
         img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i=new Intent(set_alarm.this,MainActivity.class);
                     startActivity(i);
            }
        });
        ct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment1 = new TimePickerFragment();
                newFragment1.show(getFragmentManager(), "timePicker");

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_home_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            Intent i=new Intent(set_alarm.this,MainActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
