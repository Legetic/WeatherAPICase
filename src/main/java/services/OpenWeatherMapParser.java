package main.java.services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import sun.misc.IOUtils;


import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Parser for the api from : https://openweathermap.org/api/air-pollution
 *
 */
public class OpenWeatherMapParser {





    /**
     * Parses the weather API JSON object.
     */


    public OpenWeatherMapParser() {



    }

    //Parses the weather API to gather current-day air quality information.
    public Map<String, Double> parseWeather(String stringResponse) throws JSONException, MalformedURLException {

        JSONObject response = new JSONObject(stringResponse);
        //We are using a simple hasmap for this purpose, could be changed to an object if for example,
        //More days are included.
        Map<String, Double> airQualityMap = new HashMap<>();

        JSONArray jsonArray = response.getJSONArray("list");

        //API only returns the current day so the list only has length 1.
        JSONObject airOfToday = jsonArray.getJSONObject(0);
        //Air Quality Index
        Double aqi = (double) airOfToday.getJSONObject("main").getInt("aqi");
        airQualityMap.put("aqi", aqi);

        //Components of the air quality.
        //Iterating on JSONObject: https://www.baeldung.com/jsonobject-iteration
        JSONObject components = airOfToday.getJSONObject("components");
        components.keys().forEachRemaining(key -> {

            //Exception occurs if the API includes an extra non-double value in components
            try { //Unchecked
                Double value = components.getDouble(key);
                //Save entry.
                airQualityMap.put(key, value);
            } catch (ClassCastException e) {

                System.out.println("JSONObject has value of different type: " + e.getMessage());
            }
        });

        return airQualityMap;
    }



}
