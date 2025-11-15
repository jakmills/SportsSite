package com.jash;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import io.github.cdimascio.dotenv.Dotenv;

public class newsAPI {
    
    public newsAPI() {}
    
    private String getApikey(){
        Dotenv dotnev = Dotenv.load();
        return dotnev.get("NEWS_API_KEY");
    }

    public String getInfo() throws Exception {
        String apiKey = getApikey();
        String category = "Premier League";
        String size = "5";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
            .url("https://newsapi.org/v2/everything?q=" + category +"&pageSize="+ size +"&sortBy=popularity&apiKey=" + apiKey)
            .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
