package com.thelastrobmitch.newbostontutorial;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by Robert on 11/10/2017.
 */

public class HandlingXMLStuff extends DefaultHandler {

    XMLDataCollected info = new XMLDataCollected();
    private boolean bCountry;

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        // in the above:
        // localName is heading from xml ..., the heading in the <>, eg city
        // attributes ... the word that identifies the actual data, "value" or "name"

        if (localName.equals("city")) {
            String city = attributes.getValue("name");
            info.setCity(city);
        } else if (localName.equals("temperature")) {
            String temp = attributes.getValue("value");
            info.setTemp(Float.parseFloat(temp));
        } else if (localName.equals("speed")) {
            String windSpeed = attributes.getValue("name");
            info.setWindSpeed(windSpeed);
        } else if (localName.equals("direction")) {
            String windDirection = attributes.getValue("name");
            info.setWindDirection(windDirection);
        } else if (qName.equals("country")) {
            bCountry = true;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (bCountry){
            String country = new String(ch, start, length);
            info.setCountry(country);
            bCountry = false;
        }
    }

    public String getInformation() {
        return info.dataToString();
    }
}
