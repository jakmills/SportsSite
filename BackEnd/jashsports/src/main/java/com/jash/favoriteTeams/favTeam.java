package com.jash.favoriteTeams;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.jash.database.database;

public class favTeam {

    // Returns user's favorite teams as JSON
        public String getUserFavTeams(String userID) {
        List<Map<String, String>> teams = new ArrayList<>();

        String sql = "SELECT t.teamID, t.Name AS teamName, l.Name AS leagueName, l.Country " +
                    "FROM favorite f " +
                    "JOIN team t ON f.team_teamID = t.teamID " +
                    "LEFT JOIN league l ON t.league_leagueID = l.leagueID " +
                    "WHERE f.user_userID = ?;";

        try {
            database db = new database();
            try (Connection conn = db.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, userID);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Map<String, String> team = new HashMap<>();
                        team.put("name", rs.getString("teamName"));
                        team.put("league", rs.getString("leagueName"));
                        teams.add(team);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("teams", teams);

        return new Gson().toJson(response);
    }


    // Adds a favorite by userID + teamName
    public boolean addUserFavTeam(String userID, String teamName) {
        String sql = "INSERT INTO favorite (user_userID, team_teamID) " +
                     "SELECT ?, teamID FROM team WHERE Name = ?;";
        try {
            database db = new database();
            try (Connection conn = db.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, userID);
                ps.setString(2, teamName);
                int rows = ps.executeUpdate();
                return rows > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Remove favorite by userID + teamName
    public boolean removeUserFavTeam(String userID, String teamName) {
        String sql = "DELETE FROM favorite " +
                     "WHERE user_userID = ? AND team_teamID = (SELECT teamID FROM team WHERE Name = ?);";
        try {
            database db = new database();
            try (Connection conn = db.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, userID);
                ps.setString(2, teamName);
                int rows = ps.executeUpdate();
                return rows > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Add user
    public boolean addUser(String userID) {
        String sql = "INSERT INTO user (userID) VALUES (?);";
        try {
            database db = new database();
            try (Connection conn = db.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, userID);
                ps.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Check existence
    public boolean userExists(String userID) {
        String sql = "SELECT 1 FROM user WHERE userID = ? LIMIT 1;";
        try {
            database db = new database();
            try (Connection conn = db.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, userID);
                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Returns list of teams that have teamName in their name
    public String getTeamByName(String teamName, String userID) {
        List<String> teams = new ArrayList<>();

        String sql = """
            SELECT
                t.teamID,
                t.Name,
                CASE
                    WHEN f.favoriteID IS NOT NULL THEN 1
                    ELSE 0
                END AS is_favorite
            FROM team t
            LEFT JOIN favorite f
                ON f.team_teamID = t.teamID
                AND f.user_userID = ?
            WHERE LOWER(t.Name) LIKE LOWER(?);
            """;

        try (Connection conn = new database().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userID);
            ps.setString(2, "%" + teamName.trim() + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String name = rs.getString("Name");
                    boolean isFavorite = rs.getInt("is_favorite") == 1;
                    teams.add(isFavorite ? name + "*" : name);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Gson().toJson(teams);
    }

}