package com.jash;

import com.jash.newsAPI.newsAPI;

public class Main {
    public static void main(String[] args) {
        //System.out.println("Hello world!");
        //soccerAPIAPI test = new soccerAPI();
        //footballDATA test = new footballDATA();
        newsAPI test = new newsAPI();

        try {
            test.getBundesligaNews();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}