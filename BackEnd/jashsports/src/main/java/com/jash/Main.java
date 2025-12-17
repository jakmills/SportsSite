package com.jash;

import com.jash.favoriteTeams.favTeam;
import com.jash.footballDATA.Match;
import com.jash.footballDATA.footballDATA;
import com.jash.newsAPI.NewsAPI;
import java.util.List;
import java.util.Map;

import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {

        // Set up javalin server
        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(rule -> rule.anyHost());
            });
        });

        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "7000"));
        app.start(port);

        footballDATA fd = new footballDATA();

        app.get("/football/currentMatches", ctx -> {
            try {
                Map<String, Map<String, List<Match>>> groupedMatches = fd.getAllMatchesGroupedByDate();
                ctx.json(groupedMatches);
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

        app.get("/userid/{email}", ctx -> {
            firebase fb = new firebase();
            String email = ctx.pathParam("email"); // Automatically decodes
            favTeam ft = new favTeam();
            try {
                String uid = fb.getUidByEmail(email);
                if (uid != null) {
                    ctx.result(uid);
                    if (ft.userExists(uid)) {
                        ctx.result("User found in favorite teams database");
                    } else {
                        ft.addUser(uid);
                        ctx.result("New user added to favorite teams database");
                    }
                } else {
                    ctx.status(404).result("User not found");
                }
            } catch (Exception e) {
                ctx.status(500).result("Error: " + e.getMessage());
            }
        });

        app.get("/searchedTeams/{input}/{userID}", ctx -> {
            String input = ctx.pathParam("input").replace("-", " ");
            String userID = ctx.pathParam("userID");
            System.out.println(userID);
            System.out.println(input);
            favTeam ft = new favTeam();
            System.out.println(input);
            try {
                String teamsJson = ft.getTeamByName(input, userID);
                ctx.contentType("application/json").result(teamsJson);
                System.out.println(teamsJson);
            } catch (Exception e) {
                ctx.status(500).result("Error: " + e.getMessage());
            }
        });

        app.get("/unfavorite/{teamName}/{userID}", ctx -> {
            String teamName = ctx.pathParam("teamName").replace("-", " ");
            String userID = ctx.pathParam("userID");
            favTeam ft = new favTeam();
            try {
                boolean success = ft.removeUserFavTeam(userID, teamName);
                if (success) {
                    ctx.result("Team unfavorited successfully");
                } else {
                    ctx.status(500).result("Failed to unfavorite team");
                }
            } catch (Exception e) {
                ctx.status(500).result("Error: " + e.getMessage());
            }
        });

        app.get("favorite/{teamName}/{userID}", ctx -> {
            String teamName = ctx.pathParam("teamName").replace("-", " ");
            String userID = ctx.pathParam("userID");
            favTeam ft = new favTeam();
            System.out.println(teamName);
            try {
                boolean success = ft.addUserFavTeam(userID, teamName);
                if (success) {
                    ctx.result("Team favorited successfully");
                } else {
                    ctx.status(500).result("Failed to favorite team");
                }
            } catch (Exception e) {
                ctx.status(500).result("Error: " + e.getMessage());
            }
        });

        app.get("/favoriteTeams/{userID}", ctx -> {
            String userID = ctx.pathParam("userID");
            favTeam ft = new favTeam();
            try {
                String favTeamsJson = ft.getUserFavTeams(userID);
                System.out.println(favTeamsJson);
                ctx.contentType("application/json").result(favTeamsJson);
            } catch (Exception e) {
                ctx.status(500).result("Error: " + e.getMessage());
            }
        });

    }
}