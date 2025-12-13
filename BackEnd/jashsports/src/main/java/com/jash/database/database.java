package com.jash.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class database {

    //private static Dotenv dotenv = Dotenv.load();

    // Gets DB credentials from .env file
    // private static String DB_URL = dotenv.get("DB_URL");
    // private static String DB_USERNAME = dotenv.get("DB_USERNAME");
    // private static String DB_PASSWORD = dotenv.get("DB_PASSWORD");

    private String DB_URL;
    private String DB_USERNAME;
    private String DB_PASSWORD;

    public database() {
        DB_URL = System.getenv("DB_URL");
        DB_USERNAME = System.getenv("DB_USERNAME");
        DB_PASSWORD = System.getenv("DB_PASSWORD");

        if (DB_URL == null || DB_USERNAME == null || DB_PASSWORD == null) {
            throw new IllegalStateException("Database environment variables are not set");
        }
    }

    // Establishes and returns a connection to the database
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }
    
}
