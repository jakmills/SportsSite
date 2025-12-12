package com.jash.footballDATA;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import io.github.cdimascio.dotenv.Dotenv;

public class footballDATA {

    public footballDATA() {
    }

    private String getApikey() {
        Dotenv dotnev = Dotenv.load();
        return dotnev.get("FOOTBALLDATA_API_KEY");
    }

    public ArrayList<Match> getAllMatches() throws Exception {
        try {
            String apiKey = getApikey();
            OkHttpClient client = new OkHttpClient();
            LocalDate today = LocalDate.now();
            Request request = new Request.Builder()
                    .url("https://api.football-data.org/v4/matches?dateFrom="
                            + today.toString() + "&dateTo=" + today.toString())
                    .addHeader("X-Auth-Token", apiKey)
                    .build();
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new RuntimeException("API request failed: " + response.code());
            }
            String json = response.body().string();
            Gson gson = new Gson(); // Parse the top-level object
            MatchesResponse data = gson.fromJson(json, MatchesResponse.class);
            return data.matches; // This is your ArrayList<Match>
        } catch (Exception e) {
            return null;
        }
    }

    public Map<String, List<Match>> getAllMatchesGroupedByLeague() {
        List<Match> allMatches = null;
        try {
            allMatches = getAllMatches();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        Map<String, List<Match>> groupedMatches = allMatches.stream()
                .collect(Collectors.groupingBy(
                        match -> match.competition.name));

        return groupedMatches;
    }
}
