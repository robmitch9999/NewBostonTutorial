package com.thelastrobmitch.newbostontutorial;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Robert on 31/08/2017.
 */

public class MyMenu extends ListActivity{

    String classes[] = {"StatusBar", "SeekBarVolume","Voice", "TextToVoice", "startingPoint", "GLExample", "Accelerate", "SQLiteExample", "TextPlay", "Email",
            "Camera", "Data", "ForecastFromJSON", "WeatherFromXML",
            "Graphics", "GFXSurface", "SoundStuff", "Slider", "Tabs", "SimpleBrowser",
            "Flipper", "SharedPrefs", "InternalData", "ExternalData"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // the following two lines are supposed to deliver full screen ... but don't seem to 
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setListAdapter(new ArrayAdapter<String>(MyMenu.this,
                android.R.layout.simple_list_item_1, classes));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String cheese =  classes[position];

        try {
            Class ourClass = Class.forName("com.thelastrobmitch.newbostontutorial." + cheese);
            Intent ourIntent = new Intent(MyMenu.this, ourClass);
            startActivity(ourIntent);
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater blowUp = getMenuInflater();
        blowUp.inflate(R.menu.cool_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.aboutUs:
                Intent i = new Intent("com.thelastrobmitch.newbostontutorial.ABOUTUS");
                startActivity(i);

                break;
            case R.id.preferences:
                Intent p = new Intent("com.thelastrobmitch.newbostontutorial.PREFS");
                startActivity(p);
                break;
            case R.id.exit:
                finish();
                break;
        }
        return false;
    }
}
