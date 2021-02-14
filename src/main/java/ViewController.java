package main.java;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import main.java.services.ApiContentFetcher;
import main.java.services.OpenWeatherMapAPI;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Primary ViewController for the application.
 */
public class ViewController implements Initializable {

    private ResourceBundle bundle;

    //FXML components
    @FXML
    private TextArea airResultList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = resources;

        //Initialize contact with API
        ApiContentFetcher apiContentFetcher = new ApiContentFetcher();
        OpenWeatherMapAPI openWeatherMapAPI = new OpenWeatherMapAPI(apiContentFetcher);

        //Populate List with API data.
        populateAirResultList(openWeatherMapAPI.getAirInformationReadable());

    }

    /**
     * populates the air pollution list
     *
     * @param airInfo
     */
    private void populateAirResultList(List<String> airInfo) {

        for (String info : airInfo) {
            airResultList.appendText(info);
        }

    }


}
