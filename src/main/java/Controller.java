package main.java;

import javafx.fxml.Initializable;
import main.java.services.OpenWeatherMapParser;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private ResourceBundle bundle;

    OpenWeatherMapParser apiParser = new OpenWeatherMapParser();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = resources;

        apiParser.parseWeather();


    }


}
