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
        Dotenv dotnev = Dotenv.load();
        return dotnev.get("FOOTBALLDATA_API_KEY");
    }

     public ArrayList<Match> getMatches(String league) throws Exception {
        try {
        String apiKey = getApikey();
        OkHttpClient client = new OkHttpClient();
        LocalDate today = LocalDate.now();
        Request request = new Request.Builder()
            .url("https://api.football-data.org/v4/competitions/" + getLeagueID(league) + "/matches?dateFrom="+ today.toString() +"&dateTo="+ today.toString())
            .addHeader("X-Auth-Token", apiKey)
            .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new RuntimeException("API request failed: " + response.code());
        } 
        String json = response.body().string();
        Gson gson = new Gson();     // Parse the top-level object
        MatchesResponse data = gson.fromJson(json, MatchesResponse.class);
        return data.matches; // This is your ArrayList<Match>
        } catch (Exception e) {
            return null;
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
