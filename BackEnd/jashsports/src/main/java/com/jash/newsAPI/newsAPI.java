package com.jash.newsAPI;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import io.github.cdimascio.dotenv.Dotenv;

import com.google.gson.Gson;

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

    public void getPremierLeagueNews(){
        try{
            String apiKey = getApikey();
            String urlString = "https://newsapi.org/v2/everything?q=Premier%20League&pageSize=5&sortBy=popularity&apiKey=" + apiKey;

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                .url(urlString)
                .build();
            Response response = client.newCall(request).execute();
            String json = response.body().string();

            // Parses Json
            Gson gson = new Gson();
            NewsResponse newsResponse = gson.fromJson(json, NewsResponse.class);

            // Prints articles
            for (Article article : newsResponse.articles) {
                System.out.println("Title: " + article.title);
                System.out.println("Description: " + article.description);
                System.out.println("URL: " + article.url);
                System.out.println("Published At: " + article.publishedAt);
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getLaLigaNews(){
        try{
            String apiKey = getApikey();
            String urlString = "https://newsapi.org/v2/everything?q=La%20Liga&pageSize=5&sortBy=popularity&apiKey=" + apiKey;

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                .url(urlString)
                .build();
            Response response = client.newCall(request).execute();
            String json = response.body().string();

            // Parses Json
            Gson gson = new Gson();
            NewsResponse newsResponse = gson.fromJson(json, NewsResponse.class);

            // Prints articles
            for (Article article : newsResponse.articles) {
                System.out.println("Title: " + article.title);
                System.out.println("Description: " + article.description);
                System.out.println("URL: " + article.url);
                System.out.println("Published At: " + article.publishedAt);
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getSerieANews(){
        try{
            String apiKey = getApikey();
            String urlString = "https://newsapi.org/v2/everything?q=Serie%20A&pageSize=5&sortBy=popularity&apiKey=" + apiKey;

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                .url(urlString)
                .build();
            Response response = client.newCall(request).execute();
            String json = response.body().string();

            // Parses Json
            Gson gson = new Gson();
            NewsResponse newsResponse = gson.fromJson(json, NewsResponse.class);

            // Prints articles
            for (Article article : newsResponse.articles) {
                System.out.println("Title: " + article.title);
                System.out.println("Description: " + article.description);
                System.out.println("URL: " + article.url);
                System.out.println("Published At: " + article.publishedAt);
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getBundesligaNews(){
        try{
            String apiKey = getApikey();
            String urlString = "https://newsapi.org/v2/everything?q=Bundesliga&pageSize=5&sortBy=popularity&apiKey=" + apiKey;

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                .url(urlString)
                .build();
            Response response = client.newCall(request).execute();
            String json = response.body().string();

            // Parses Json
            Gson gson = new Gson();
            NewsResponse newsResponse = gson.fromJson(json, NewsResponse.class);

            // Prints articles
            for (Article article : newsResponse.articles) {
                System.out.println("Title: " + article.title);
                System.out.println("Description: " + article.description);
                System.out.println("URL: " + article.url);
                System.out.println("Published At: " + article.publishedAt);
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getLigue1News(){
        try{
            String apiKey = getApikey();
            String urlString = "https://newsapi.org/v2/everything?q=Ligue%201&pageSize=5&sortBy=popularity&apiKey=" + apiKey;

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                .url(urlString)
                .build();
            Response response = client.newCall(request).execute();
            String json = response.body().string();

            // Parses Json
            Gson gson = new Gson();
            NewsResponse newsResponse = gson.fromJson(json, NewsResponse.class);

            // Prints articles
            for (Article article : newsResponse.articles) {
                System.out.println("Title: " + article.title);
                System.out.println("Description: " + article.description);
                System.out.println("URL: " + article.url);
                System.out.println("Published At: " + article.publishedAt);
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}