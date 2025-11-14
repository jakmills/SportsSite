package com.jash;
import io.github.cdimascio.dotenv.Dotenv;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        
        Dotenv dotnev = Dotenv.load();
        String apikey = dotnev.get("API_KEY");

        System.out.println(apikey);
    }
}