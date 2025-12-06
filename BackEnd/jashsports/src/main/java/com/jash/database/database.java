package com.jash.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class database {

    private static Dotenv dotenv = Dotenv.load();

    // Gets DB credentials from .env file
    private static String DB_URL = dotenv.get("DB_URL");
    private static String DB_USERNAME = dotenv.get("DB_USERNAME");
    private static String DB_PASSWORD = dotenv.get("DB_PASSWORD");

    // Establishes and returns a connection to the database
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }
    
}
