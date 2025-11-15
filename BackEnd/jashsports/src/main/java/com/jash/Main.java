package com.jash;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        //soccerAPIAPI test = new soccerAPI();
        //footballDATA test = new footballDATA();
        newsAPI test = new newsAPI();

        try {
            System.out.println(test.getInfo());
        } catch (Exception e) {
        }
    }
}