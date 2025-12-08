package com.jash.favoriteTeams;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.jash.database.database;

public class favTeam {
    public String getUserFavTeams(String userID) {
        String team = "";

        try{
            database db = new database();
            db.getConnection();

            // Query that retrieves a specified user's favorite teams (must enter in the uniqueID assigned by firebase)
            PreparedStatement ps = db.getConnection().prepareStatement("SELECT t.teamID, t.Name AS teamName, l.Name AS leagueName, l.Country\r\n" +
                                "FROM favorite f\r\n" +
                                "JOIN team t ON f.team_teamID = t.teamID\r\n" +
                                "LEFT JOIN league l ON t.league_leagueID = l.leagueID\r\n" +
                                "WHERE f.user_userID = " + userID + ";\r\n");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                team = rs.getString("teamName");
            }
            System.out.println(team); // for testing

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean addUserFavTeam(String userID, String teamName) {
        try{
            database db = new database();
            db.getConnection();

            // Query that adds a favorite team for a specific user (must enter in the uniqueID assigned by firebase)
            PreparedStatement ps = db.getConnection().prepareStatement("INSERT INTO favorite (user_userID, team_teamID) VALUES (" + userID + "select teamID FROM team WHERE Name = '" + teamName + "');");
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeUserFavTeam(String UserID, String teamName) {
        try{
            database db = new database();
            db.getConnection();

            // Query that removes a favorite team for a specific user (must enter in the uniqueID assigned by firebase)
            PreparedStatement ps = db.getConnection().prepareStatement("DELETE FROM favorite WHERE user_userID = '" + UserID + "' AND team_teamID = (SELECT teamID FROM team WHERE Name = '" + teamName + "');");
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addUser(String userID) {
        try{
            database db = new database();
            db.getConnection();

            // Query that adds a favorite team for a specific user (must enter in the uniqueID assigned by firebase)
            PreparedStatement ps = db.getConnection().prepareStatement("INSERT INTO user (userID) VALUES ('" + userID + "');");
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean userExists(String userID) {
        try {
            database db = new database();
            db.getConnection();
            PreparedStatement ps = db.getConnection().prepareStatement("SELECT 1 FROM user WHERE userID = ? LIMIT 1;");
            ps.setString(1, userID);
            ResultSet rs = ps.executeQuery();
            boolean exists = rs.next();
            rs.close();
            ps.close();
            return exists;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
