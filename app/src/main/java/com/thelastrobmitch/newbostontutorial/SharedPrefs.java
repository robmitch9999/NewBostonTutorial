package com.thelastrobmitch.newbostontutorial;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Robert on 18/09/2017.
 */

public class SharedPrefs extends Activity implements View.OnClickListener{

    // this demonstrates use of shared preferences.
    // Note that you can only save "natives", String, boolean, long, ...
    // Shared preferences are not for DATA in the general sense

    EditText sharedData;
    TextView dataResults;
    public static String filename = "MySharedString";
    SharedPreferences someData;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sharedpreferences);
        setupVariables();

        someData = getSharedPreferences(filename, 0);
    }

    private void setupVariables() {
        Button save = (Button) findViewById(R.id.bSave);
        Button load = (Button) findViewById(R.id.bLoad);
        Button clear = (Button) findViewById(R.id.bClearSharedPreferences);

        sharedData = (EditText) findViewById(R.id.etSharedPrefs);
        dataResults = (TextView) findViewById(R.id.tvLoadSharedPrefs);
        save.setOnClickListener(this);
        load.setOnClickListener(this);
        clear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        SharedPreferences.Editor editor = someData.edit();
        switch (view.getId()){
            case R.id.bSave:
                String stringData = sharedData.getText().toString();
                editor.putString("sharedString", stringData);
                editor.apply();
                break;
            case R.id.bLoad:
                someData = getSharedPreferences(filename, 0);
                String dataReturned = someData.getString("sharedString", "nothing found");
                dataResults.setText(dataReturned);
                break;
            case R.id.bClearSharedPreferences:
                someData = getSharedPreferences(filename, 0);
                someData.edit().clear().apply();
                break;
        }
    }
}
