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
        Dotenv dotnev = Dotenv.load();
        return dotnev.get("NEWS_API_KEY");
    }

    public String getNewsJson(String league) throws Exception {
        String apiKey = getApikey();
        String query = (league + " football").replace(" ", "%20");
        LocalDate twoDaysAgo = LocalDate.now().minusDays(2);
        String fromDate = twoDaysAgo.toString();
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
            simplifiedArticles.add(sa);
        }

        return gson.toJson(simplifiedArticles);
    }

    public void getPremierLeagueNews(){
        try{
            String apiKey = getApikey();
            LocalDate twoDaysAgo = LocalDate.now().minusDays(2);
            String fromDate = twoDaysAgo.toString();
            String urlString = "https://newsapi.org/v2/everything?q=Premier%20League%20football&pageSize=6&sortBy=popularity&language=en&from=" + fromDate + "&apiKey=" + apiKey;

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
            LocalDate twoDaysAgo = LocalDate.now().minusDays(2);
            String fromDate = twoDaysAgo.toString();
            String urlString = "https://newsapi.org/v2/everything?q=La%20Liga%20football&pageSize=6&sortBy=popularity&language=en&from=" + fromDate + "&apiKey=" + apiKey;

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
            LocalDate twoDaysAgo = LocalDate.now().minusDays(2);
            String fromDate = twoDaysAgo.toString();
            String urlString = "https://newsapi.org/v2/everything?q=Serie%20A%20football&pageSize=6&sortBy=popularity&language=en&from=" + fromDate + "&apiKey=" + apiKey;

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
            LocalDate twoDaysAgo = LocalDate.now().minusDays(2);
            String fromDate = twoDaysAgo.toString();
            String urlString = "https://newsapi.org/v2/everything?q=Bundesliga%20football&pageSize=6&sortBy=popularity&language=en&from=" + fromDate + "&apiKey=" + apiKey;

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
            LocalDate twoDaysAgo = LocalDate.now().minusDays(2);
            String fromDate = twoDaysAgo.toString();
            String urlString = "https://newsapi.org/v2/everything?q=Ligue%201%20football&pageSize=6&sortBy=popularity&language=en&from=" + fromDate + "&apiKey=" + apiKey;

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