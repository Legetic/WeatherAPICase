package main.java.services;


import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ApiContentFetcher {

    //Taken from here: https://dzone.com/articles/how-to-parse-json-data-from-a-rest-api-using-simpl
    public String readUrl(String urlString) throws Exception {
        StringBuilder resultString = new StringBuilder();

        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //Set request method, we want to get information.
        conn.setRequestMethod("GET");
        conn.connect();

        //Get responsecode from http request
        int responsecode = conn.getResponseCode();

        if(responsecode != 200){ // 200 = ok
            throw new RuntimeException("HttpResponseCode: " +responsecode);
        } else
        {
            //Read the data from the connection
            Scanner sc = new Scanner(url.openStream());
            while(sc.hasNext()) // Iterator
            {
                resultString.append(sc.nextLine());
            }

            sc.close();
        }
        return resultString.toString();
    }



}
