package com.jash;


import com.jash.footballDATA.footballDATA;
import com.jash.newsAPI.NewsAPI;

import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {

        //sets up javalin server
        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(rule -> rule.anyHost());
            });
        });

        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "7000"));
        app.start(port);

        footballDATA fd = new footballDATA();
        app.get("/football/{league}", ctx -> {
            try {
                String league = ctx.pathParam("league").replace("-", " ");
                String matchesJson = fd.getMatches(league);
                ctx.contentType("application/json").result(matchesJson);
            } catch (Exception e) {
                ctx.status(500).result("Error fetching matches: " + e.getMessage());
            }
        });


        NewsAPI newsAPI = new NewsAPI();
        app.get("/", ctx -> ctx.result("SportsSite API is running!"));

        app.get("/news/{league}", ctx -> {
            String league = ctx.pathParam("league").replace("-", " ");
            try {
                String news = newsAPI.getNewsJson(league);
                ctx.contentType("application/json").result(news);
            } catch (Exception e) {
                ctx.status(500).result("Error: " + e.getMessage());
            }
        });

    }
}