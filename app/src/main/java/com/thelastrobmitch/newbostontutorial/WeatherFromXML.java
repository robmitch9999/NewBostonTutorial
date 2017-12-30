package com.thelastrobmitch.newbostontutorial;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Robert on 11/10/2017.
 */

public class WeatherFromXML extends Activity implements View.OnClickListener{

    private static final String TAG = "WeatherFromXML";
    TextView currentWeather;
    EditText city, country;
    //http://api.openweathermap.org/data/2.5/weather?q=newcastle,England&mode=xml&APPID=df763603a1006917a1873eac922f147e
    static final String appid = "df763603a1006917a1873eac922f147e";

    static final String baseURL = "https://api.openweathermap.org/data/2.5/weather?q=";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_from_xml);

        city = (EditText) findViewById(R.id.etCity);
        country = (EditText) findViewById(R.id.etCountry);
        Button getWeather = (Button) findViewById(R.id.bGetWeather);
        getWeather.setOnClickListener(this);
        currentWeather = (TextView)findViewById(R.id.tvWeather);
    }

    @Override
    public void onClick(View v) {
        String c = city.getText().toString();
        String s = country.getText().toString();
        StringBuilder URL = new StringBuilder(baseURL);
        URL.append(c);
        if (!TextUtils.isEmpty(s)) {
            URL.append(",").append(s);
        }
        URL.append("&mode=xml&APPID=").append(appid);
        String fullURL = URL.toString();
        Log.d("onClick", fullURL);

        new retrieveFileTask().execute(fullURL);

    }

    class retrieveFileTask extends AsyncTask<String, Void, String> {
        String information = null;

        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
            currentWeather.setText(information);
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                //Log.d(TAG,"in the try block");
                URL website = new URL(params[0]);
                //Log.d(TAG,"URL= " + fullURL);

                // getting xml reader to parse data
                SAXParserFactory spf = SAXParserFactory.newInstance();
                //Log.d(TAG,"created spf");

                SAXParser sp = spf.newSAXParser();
                //Log.d(TAG,"created sp");
                XMLReader xr = sp.getXMLReader();
                //Log.d(TAG,"created xr");

                HandlingXMLStuff doingWork = new HandlingXMLStuff();
                //Log.d(TAG,"created doingWork");
                xr.setContentHandler(doingWork);
                Log.d(TAG,"set content handler");
                xr.parse(new InputSource(website.openStream()));
                Log.d(TAG,"parsed");
                information = doingWork.getInformation();
                Log.d(TAG,"created information");

            } catch (Exception e) {
                information = "error: " + e.getMessage() + " >> " + e.toString();
            }
            return information;
        }
    }
}
