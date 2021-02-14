package test.java;

import main.java.services.ApiContentFetcher;
import main.java.services.OpenWeatherMapAPI;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class OpenWeatherMapAPITest {

    /**
     * Testing of the API communication
     */
    @Test
    public void OpenWeatherMapAPICommunicationTest() {
        ApiContentFetcher apiContentFetcher = new ApiContentFetcher();
        OpenWeatherMapAPI openWeatherMapAPI = new OpenWeatherMapAPI(apiContentFetcher);

        List<String> apiResult = openWeatherMapAPI.getAirInformationReadable();

        Assert.assertTrue(!apiResult.isEmpty());
        Assert.assertNotNull(apiResult);


    }
}
