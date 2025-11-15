package com.jash;
import com.google.gson.Gson;
import com.jash.footballDATA.Match;
import com.jash.footballDATA.MatchesResponse;
import com.jash.footballDATA.footballDATA;

public class Main {
    public static void main(String[] args) {
        //soccerAPIAPI test = new soccerAPI();
        footballDATA test = new footballDATA();
        //newsAPI test = new newsAPI();

        String JSONString = "";

        try {
            JSONString = test.getInfo();
        } catch (Exception e) {
        }
        Gson gson = new Gson();
        MatchesResponse response = gson.fromJson(JSONString, MatchesResponse.class);

        for (Match m : response.matches) {
            System.out.println("Match ID: " + m.id);
        }

    }
}