package com.jash.soccerAPI;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class LiveScoreResponse {
    public List<MatchData> response;

    public static class MatchData {
        public Fixture fixture;
        public League league;
        public Teams teams;
        public Goals goals;
    }

    public static class Fixture {
        public int id;
        public Status status;
    }

    public static class Status {
        public String statusLong; // e.g., "First Half"
        
        public String statusShort; // e.g., "1H" or "HT"
        
        public Integer elapsed; // The match minute (e.g., 45)
    }

    public static class League {
        public String name;
        public String logo;
    }

    public static class Teams {
        public TeamDetails home;
        public TeamDetails away;
    }

    public static class TeamDetails {
        public String name;
        public String logo;
    }

    public static class Goals {
        public Integer home;
        public Integer away;
    }
}
