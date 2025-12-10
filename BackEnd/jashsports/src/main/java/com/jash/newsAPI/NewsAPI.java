package com.jash.newsAPI;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import io.github.cdimascio.dotenv.Dotenv;

import com.google.gson.Gson;
import java.util.List;
import java.time.LocalDate;

public class NewsAPI {
    
    public NewsAPI() {}
    
    private String getApikey(){
        String apiKey = System.getenv("NEWS_API_KEY");
        if (apiKey != null) {
            return apiKey;
        }
        Dotenv dotnev = Dotenv.load();
        return dotnev.get("NEWS_API_KEY");
    }

    public String getNewsJson(String league) throws Exception {
        String apiKey = getApikey();
        String query = (league + " football").replace(" ", "%20");
        LocalDate sevenDaysAgo = LocalDate.now().minusDays(7);
        String fromDate = sevenDaysAgo.toString();
        String urlString = "https://newsapi.org/v2/everything?q=" + query + "&pageSize=6&sortBy=popularity&language=en&from=" + fromDate + "&apiKey=" + apiKey;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
            .url(urlString)
            .build();
        Response response = client.newCall(request).execute();
        String json = response.body().string();

        // Parses Json
        Gson gson = new Gson();
        NewsResponse newsResponse = gson.fromJson(json, NewsResponse.class);

        // Create a list of simplified articles
        List<SimplifiedArticle> simplifiedArticles = new java.util.ArrayList<>();
        for (Article article : newsResponse.articles) {
            SimplifiedArticle sa = new SimplifiedArticle();
            sa.title = article.title;
            sa.caption = article.description != null ? article.description : "";
            sa.datePosted = article.publishedAt;
            sa.image = article.urlToImage != null ? article.urlToImage : "";
            sa.url = article.url != null ? article.url : "";
            simplifiedArticles.add(sa);
        }

        return gson.toJson(simplifiedArticles);
    }
}