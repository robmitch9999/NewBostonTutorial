package com.thelastrobmitch.newbostontutorial;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Robert on 8/10/2017.
 */

public class ForecastFromJSON extends Activity {
    private TextView httpStuff;
    private static final String TAG = "ForecastFromJSON";
    JSONObject json;
    //http://api.openweathermap.org/data/2.5/weather?q=newcastle,England&mode=xml&APPID=df763603a1006917a1873eac922f147e
    static final String appid = "df763603a1006917a1873eac922f147e";
    static final String baseURL = "https://api.openweathermap.org/data/2.5/forecast?q=";
    static final String interURL = "&mode=json&APPID=" + appid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.httpex);
        httpStuff = (TextView) findViewById(R.id.tvHttp);
        new Read().execute("Sydney");
    }


    public JSONObject weather(String city) throws IOException, JSONException {

        String returned = null;
        StringBuilder url = new StringBuilder(baseURL);

        url.append(city).append(interURL);
        Log.d(TAG, url.toString());

        //Log.d(TAG, weatherForecast.toString());

        try{
            //Log.d(TAG, "got into the try block");

            URL website = new URL(url.toString());
            //Log.d(TAG, "Got the website: " + website.toString());
            HttpURLConnection urlConnection = (HttpURLConnection) website.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            StringBuilder sb = new StringBuilder();
            BufferedReader r = new BufferedReader(new InputStreamReader(in),1000);
            String nl = System.getProperty("line.separator");
            for (String line = r.readLine(); line != null; line =r.readLine()){
                sb.append(line).append(nl);
            }
            in.close();
            //Log.d(TAG, "closed the input stream");
            returned = sb.toString();

            //Log.d(TAG, returned);
        } catch (Exception e) {
            Log.d(TAG, "caught exception: " + e.toString() + "\n");
            e.printStackTrace();
        }

        if (!returned.isEmpty()){
            //Log.d(TAG, "returned is NOT empty! \n" + returned + "\n\n");
// you have to know the organisation of the json file intimately and work down through it
// until you get the bits you want ... in this case extracting the "list" and then
// returning the eighth member of the list, itself a json array ... I think I have it figured at
// this point
            String fullForecast = new JSONObject(returned).getString("list");
            JSONArray nextForecast = new JSONArray(fullForecast);
            JSONObject thisForecast = (JSONObject) nextForecast.get(7);

            //JSONObject smallForecast = fullForecast.getJSONObject(0);
            return thisForecast;
        } else {
            Toast.makeText(ForecastFromJSON.this, "error", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public class Read extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            //Log.d(TAG, "did this!");
            try {
                //Log.d(TAG, "and this!");
                json = weather(params[0]);
                //Log.d(TAG, "and this also!");
                // could do further screwing around with the JSON file which looks a bit
                // like this at this point:

                // {
                // "dt":1507960800,
                // "main":{"temp":290.651,"temp_min":290.651,"temp_max":290.651,"pressure":1031.03,"sea_level":1037.17,"grnd_level":1031.03,"humidity":87,"temp_kf":0},
                // "weather":[{"id":500,"main":"Rain","description":"light rain","icon":"10d"}],
                // "clouds":{"all":92},
                // "wind":{"speed":3.4,"deg":128.503},
                // "rain":{"3h":0.395},
                // "sys":{"pod":"d"},
                // "dt_txt":"2017-10-14 06:00:00"
                // }

                return json.toString(); //.getString("main");
            } catch (IOException e) {
                Log.d(TAG, e.toString());
                e.printStackTrace();
            } catch (JSONException e) {
                Log.d(TAG, e.toString());
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            httpStuff.setText("Sydney forecast for this time tomorrow is \n\n" + result.
                    replaceAll("\\{","\\\n\\{").
                    replaceAll("\\}","\\}\\\n"));
        }
    }
}
