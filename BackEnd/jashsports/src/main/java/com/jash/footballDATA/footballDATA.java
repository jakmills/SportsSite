package com.jash.footballDATA;
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

    public String getInfo() throws Exception {
        String apiKey = getApikey();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
            .url("https://api.football-data.org/v4/matches")
            .addHeader("X-Auth-Token", apiKey)
            .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

}
