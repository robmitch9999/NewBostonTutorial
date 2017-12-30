package com.thelastrobmitch.newbostontutorial;

/**
 * Created by Robert on 11/10/2017.
 */

public class XMLDataCollected {
    float temp = 273;
    String windSpeed = null;
    String windDirection = null;
    String city = null;
    String country = null;

    public void setCity (String c) {
        city = c;
    }

    public void setCountry (String s) {
        country = s;
    }

    public void setWindSpeed (String ws) {
        windSpeed = ws;
    }
    public void setTemp (float t) {
        temp = t - 273;
    }
    public void setWindDirection (String wd) {
        windDirection = wd;
    }
    public String dataToString(){
        return "The current temperature in " + city + ", " + country + " is " +
                String.format("%.01f", temp) + " C. \nThere is a " + windSpeed + " from the " + windDirection;
    }
}
