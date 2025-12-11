package com.jash.favoriteTeams;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.jash.database.database;

public class favTeam {

    // Return a comma-separated list of team names
    public String getUserFavTeams(String userID) {
        StringBuilder teams = new StringBuilder();

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
                    boolean first = true;
                    while (rs.next()) {
                        if (!first) teams.append(", ");
                        teams.append(rs.getString("teamName"));
                        first = false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return teams.toString();
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
    public String getTeamByName(String teamName) {
        List<String> teams = new ArrayList<>();

        String sql = "SELECT Name FROM team WHERE Name LIKE ?;";

        // makes it so search is case insensitive and capitalizes first letter of each word for database match
        teamName = teamName.toLowerCase();
        String[] words = teamName.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String w : words) {
            sb.append(Character.toUpperCase(w.charAt(0)))
            .append(w.substring(1).toLowerCase())
            .append(" ");
        }
        teamName = sb.toString().trim();

        try {
            database db = new database();
            try (Connection conn = db.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, "%" + teamName + "%");
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        teams.add(rs.getString("Name"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String json = new Gson().toJson(teams);
        return json;
    }
}
