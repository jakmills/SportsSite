package com.jash.favoriteTeams;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.jash.database.database;

public class favTeam {
    public String getUserFavTeams() {
        String team = "";

        try{
            database db = new database();
            db.getConnection();

            PreparedStatement ps = db.getConnection().prepareStatement("Select t.name..."); // query that gets user fav teams
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                team = rs.getString("team_name");
            }
            System.out.println(team); // for testing

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void updateUserFavTeams() {
        try{
            database db = new database();
            db.getConnection();

            PreparedStatement ps = db.getConnection().prepareStatement(""); // query that updates user fav teams, aka if they remove a team or add one
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
