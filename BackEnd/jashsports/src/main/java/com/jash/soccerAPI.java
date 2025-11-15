package com.jash;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import io.github.cdimascio.dotenv.Dotenv;

public class soccerAPI {
    public soccerAPI() {}
    
    private String getApikey(){
        Dotenv dotnev = Dotenv.load();
        return dotnev.get("APIFOOTBALL_API_KEY");
    }

    public String getInfo() throws Exception {
        String apiKey = getApikey();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
            .url("https://v3.football.api-sports.io/injuries?team=85&season=2022")
            .addHeader("x-apisports-key", apiKey)
            .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
