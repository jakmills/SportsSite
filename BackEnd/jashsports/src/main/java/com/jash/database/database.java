package com.jash.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class database {

    static Dotenv dotenv = Dotenv.load();

    // Gets DB credentials from .env file
    private static String DB_URL = dotenv.get("DB_URL");
    private static String USER = dotenv.get("USER");
    private static String PASS = dotenv.get("PASS");

    // Establishes and returns a connection to the database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }


    
}
