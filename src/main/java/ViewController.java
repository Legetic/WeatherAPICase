package main.java;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import main.java.services.ApiContentFetcher;
import main.java.services.OpenWeatherMapParser;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ViewController implements Initializable {

    private ResourceBundle bundle;


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


    //FXML things
    @FXML
    private TextArea airResultList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = resources;

        //Field variables set and rounded (so they are allowed in the api).
        setLatLong(lat, lng);

        //Populate List
        populateAirResultList(getAirInformation());

    }

    /**populates the air pollution list
     *
     * @param airInfo
     */
    private void populateAirResultList(Map<String, Double> airInfo){

        for (String key:airInfo.keySet()) {
            airResultList.appendText(key + ": " + airInfo.get(key) + "\n");
        }

    }

    /**
     * Gets information on air pollution.
     * @return
     */
    private Map<String, Double> getAirInformation(){

        Map<String, Double> airQualityMap = new HashMap<>();

        //Building the URL.
        this.apiurl = "http://api.openweathermap.org/data/2.5/air_pollution?lat=63.825&lon=20.263&appid=" + apiKey;

        //Fetch content from the api.
        ApiContentFetcher contentFetcher = new ApiContentFetcher();
        String apiContent = null;
        try {
            apiContent = contentFetcher.readUrl(apiurl);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Parse content from the api.
        if(apiContent != null){
            OpenWeatherMapParser apiParser = new OpenWeatherMapParser();
            try {
                airQualityMap = apiParser.parseWeather(apiContent);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

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