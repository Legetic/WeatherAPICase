package main.java.services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Parser for the api from : https://openweathermap.org/api/air-pollution
 *
 */
public class OpenWeatherMapParser {

    /**
     * target Latitude
     */
    private Double lat = 63.825;
    /**
     * target Longitude
     */
    private Double lng = 20.263;

    /**
     * The url to the api that will be called.
     */
    private String apiurl;

    /**
     * API Key,
     * Should definately be hidden since my github repo is public. (Didn't though in the interest of time)
     */
    private String apiKey = "8359f56581b36d5a9eeb9b72bdc20205";

    /**
     * Parses the weather API JSON object.
     */

    public OpenWeatherMapParser(Double lat, Double lng) {

        //Field variables set and rounded (so they are allowed in the api).
        setLatLong(lat, lng);

        //Building the URL.
        this.apiurl = "http://api.openweathermap.org/data/2.5/air_pollution?lat=" + lat + "&lon=" + lng + "&appid=" + apiKey;

    }

    public OpenWeatherMapParser() {

        //Field variables set and rounded (so they are allowed in the api).
        setLatLong(lat, lng);

        //Building the URL.
        this.apiurl = "http://api.openweathermap.org/data/2.5/air_pollution?lat=63.825&lon=20.263&appid=" + apiKey;

    }

    //Parses the weather API to gather current-day air quality information.
    public Map<String, Double> parseWeather(JSONObject response) throws JSONException {

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
                Double value = (Double) components.get(key);
                //Save entry.
                airQualityMap.put(key, value);
            } catch (ClassCastException e) {

                System.out.println("JSONObject has value of different type: " + e.getMessage());
            }
        });

        return airQualityMap;
    }

    /**
     * Rounds to 2 decimal places
     * https://stackoverflow.com/questions/11701399/round-up-to-2-decimal-places-in-java
     *
     * @param lat
     * @param lng
     */
    private void setLatLong(Double lat, Double lng) {

        //kind of works like, shifting the number 2 places to the left,
        //rounding (cutting of decimals and rounding the whole number including the last 2 digits)
        //Shifts the number back by dividing by 100.0
        this.lat = Math.round(lat * 100.0) / 100.0;

        this.lng = Math.round(lng * 100.0) / 100.0;

    }

}
