package main.java.services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Parser for the api from : https://openweathermap.org/api/air-pollution
 */
public class OpenWeatherMapAPI {

    /**
     * API Key,
     * Should definately be hidden since my github repo is public. (Didn't though in the interest of time)
     */
    private String apiKey = "8359f56581b36d5a9eeb9b72bdc20205";

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
     * The http communicator, gets string response from API
     */
    private ApiContentFetcher contentFetcher;


    public OpenWeatherMapAPI(ApiContentFetcher contentFetcher) {
        this.contentFetcher = contentFetcher;
    }


    /**
     * Provides a readable/formatted list of air information
     *
     * @return
     */
    public List<String> getAirInformationReadable() {
        List<String> resultList = new ArrayList<>();

        Map<String, Double> airQualityMap = getAirInformation();

        for (String key : airQualityMap.keySet()) {
            if(key.equals("aqi")){
                //Put aqi first
                resultList.add(0,apiKeyToDescription(key) + ": " + airQualityDescription(airQualityMap.get(key)) + "\n\n");
            }else{
                resultList.add(apiKeyToDescription(key) + ": " + airQualityMap.get(key) + " μg/m^3\n\n");
            }

        }
        return resultList;
    }

    /**
     * Maps air quality number to its description.
     * @param a
     * @return
     */
    private String airQualityDescription(Double a) {
        if (a ==1.0 ) {
            return "Good";
        } else if (a ==2.0 ) {
            return "Fair";
        } else if (a ==3.0 ) {
            return "Moderate";
        } else if (a ==4.0 ) {
            return "Poor";
        } else if (a ==5.0 ) {
            return "Very Poor";
        } else {
            return "Unknown";
        }
    }

    /**
     * Gets information on air pollution.
     *
     * @return
     */
    private Map<String, Double> getAirInformation() {

        Map<String, Double> airQualityMap = new HashMap<>();

        //Building the URL.
        this.apiurl = "http://api.openweathermap.org/data/2.5/air_pollution?lat=57.708&lon=11.97&appid=" + apiKey;

        //Fetch content from the api.
        String apiContent = null;
        try {
            apiContent = contentFetcher.readUrl(apiurl);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Parse content from the api.
        if (apiContent != null) {
            try {
                airQualityMap = parseWeather(apiContent);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return airQualityMap;
    }

    /**
     * Parses the weather API string response (JSON) to gather current-day air quality information.
     */
    private Map<String, Double> parseWeather(String stringResponse) throws JSONException {

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

    /**
     * Maps api key to its description.
     *
     * @param key
     * @return
     */
    private String apiKeyToDescription(String key) {
        switch (key) {
            case "aqi":
                return "Air Quality";
            case "co":
                return "Сoncentration of CO (Carbon monoxide)";

            case "no":
                return "Сoncentration of NO (Nitrogen monoxide)";

            case "no2":
                return "Сoncentration of NO2 (Nitrogen dioxide)";

            case "o3":
                return "Сoncentration of O3 (Ozone)";

            case "so2":
                return "Сoncentration of SO2 (Sulphur dioxide)";

            case "pm2_5":
                return "Сoncentration of PM2.5 (Fine particles matter)";

            case "pm10":
                return "Сoncentration of PM10 (Coarse particulate matter)";

            case "nh3":
                return "Сoncentration of NH3 (Ammonia)";

            default:
                return "Unknown Parameter";

        }
    }


}
