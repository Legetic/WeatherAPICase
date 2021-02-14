package test.java;

import junit.framework.TestCase;
import main.java.services.ApiContentFetcher;
import org.junit.Assert;
import org.junit.Test;

import java.net.MalformedURLException;

/**
 * Testing of the fetching of string from API.
 */
public class APIContentFetcherTest {


    @Test
    public void APIContentFetcherCommunicationTest_ShouldFail() {
        ApiContentFetcher apiContentFetcher = new ApiContentFetcher();
        String result = null;

        try {
            result = apiContentFetcher.readUrl("");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertNull(result);




    }

    @Test
    public void APIContentFetcherCommunicationTest_ShouldPass() {
        ApiContentFetcher apiContentFetcher = new ApiContentFetcher();
        String result = null;

        String apiKey = "8359f56581b36d5a9eeb9b72bdc20205";
        String apiurl = "http://api.openweathermap.org/data/2.5/air_pollution?lat=57.708&lon=11.97&appid=" + apiKey;

        try {
            result = apiContentFetcher.readUrl(apiurl);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(result);

    }



}
