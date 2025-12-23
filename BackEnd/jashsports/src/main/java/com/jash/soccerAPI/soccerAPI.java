package com.jash.soccerAPI;
import com.google.gson.Gson;
import com.jash.footballDATA.MatchesResponse;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import io.github.cdimascio.dotenv.Dotenv;

public class soccerAPI {
    public soccerAPI() {}
    
    private String getApikey(){
        String key = System.getenv("APIFOOTBALL_API_KEY");
        if (key != null) {
            return key;
        }
        Dotenv dotnev = Dotenv.load();
        return dotnev.get("APIFOOTBALL_API_KEY");

    }

    public LiveScoreResponse getInfo() throws Exception {
        String apiKey = getApikey();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
            .url("https://v3.football.api-sports.io/fixtures?live=all")
            .addHeader("x-apisports-key", apiKey)
            .build();
        Response response = client.newCall(request).execute();
        String json = response.body().string();
        Gson gson = new Gson(); // Parse the top-level object
        LiveScoreResponse data = gson.fromJson(json, LiveScoreResponse.class);
        return data;
    }

}
