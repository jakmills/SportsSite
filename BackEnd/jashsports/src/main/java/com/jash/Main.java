package com.jash;

import com.jash.newsAPI.NewsAPI;

import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {

        //sets up javalin server

        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(rule -> {
                    rule.allowHost("http://localhost:5500");
                });
            });
        }).start(7000);


        NewsAPI newsAPI = new NewsAPI();
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