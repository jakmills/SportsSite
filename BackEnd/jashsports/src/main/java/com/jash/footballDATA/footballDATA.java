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
import java.time.format.DateTimeFormatter;
import java.time.ZonedDateTime;

public class footballDATA {

    public footballDATA() {
    }

    private String getApikey() {
        String key = System.getenv("FOOTBALLDATA_API_KEY");
        if (key != null) {
            return key;
        }
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
                            + today.minusDays(2).toString() + "&dateTo=" + today.plusDays(7).toString())
                    .addHeader("X-Auth-Token", apiKey)
                    .build();
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new RuntimeException("API request failed: " + response.code());
            }
            String json = response.body().string();
            Gson gson = new Gson(); // Parse the top-level object
            MatchesResponse data = gson.fromJson(json, MatchesResponse.class);
            return data.matches;
        } catch (Exception e) {
            return null;
        }
    }

    public Map<String, Map<String, List<Match>>> getAllMatchesGroupedByDate() {
        // get list of matches from api
        List<Match> allMatches = null;
        try {
            allMatches = getAllMatches();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        // nested grouping
        Map<String, Map<String, List<Match>>> groupedByDate = allMatches.stream()
                .collect(Collectors.groupingBy(
                        // outer grouping, groups by date
                        match -> {
                            // parse the iso date string and format it to just the date part
                            ZonedDateTime dateTime = ZonedDateTime.parse(match.utcDate);
                            return dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
                        },
                        // inner grouping, groups by competition
                        Collectors.groupingBy(
                                match -> match.competition.name)));

        return groupedByDate;
    }
}
