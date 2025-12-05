package com.jash.footballDATA;
import java.time.LocalDate;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import io.github.cdimascio.dotenv.Dotenv; 

public class footballDATA {

    public footballDATA() {   
    }
    
    private String getApikey(){
        String apiKey = System.getenv("FOOTBALLDATA_API_KEY");
        if (apiKey != null && !apiKey.isEmpty()) {
            return apiKey;
        }
        try {
            Dotenv dotenv = Dotenv.load();
            return dotenv.get("FOOTBALLDATA_API_KEY");
        } catch (Exception e) {
            Dotenv dotenv = Dotenv.configure().directory("BackEnd/jashsports").load();
            return dotenv.get("FOOTBALLDATA_API_KEY");
        }
    }

     public String getMatches(String league) throws Exception {
        String apiKey = getApikey();
        OkHttpClient client = new OkHttpClient();
        LocalDate today = LocalDate.now();
        Request request = new Request.Builder()
            .url("https://api.football-data.org/v4/competitions/" + getLeagueID(league) + "/matches?dateFrom="+ today.toString() +"&dateTo="+ today.toString())
            .addHeader("X-Auth-Token", apiKey)
            .build();
        Response response = client.newCall(request).execute();
        try {
            if (!response.isSuccessful()) {
                throw new RuntimeException("API request failed: " + response.code());
            }
            String json = response.body().string();
            Gson gson = new Gson();     // Parse the top-level object
            MatchesResponse data = gson.fromJson(json, MatchesResponse.class);
            // Return the matches list as a JSON string
            return gson.toJson(data.matches);
        } finally {
            if (response.body() != null) {
                try { response.body().close(); } catch (Exception ex) { /* ignore */ }
            }
        }
    }

    public int getLeagueID(String league) {
        switch (league.toLowerCase().replaceAll("\\s", "")){
            case "championsleague":
                return 2001;
            case "premierleague":
                return 2021;
            case "laliga":
                return 2014;
            case "seriea":
                return 2019;
            default:
                return -1;
        }
    }
}
