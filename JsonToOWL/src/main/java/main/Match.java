package main;

import java.util.ArrayList;

public class Match {
    private String date;
    private String homeTeam;
    private String awayTeam;
    private long homeTeamFullTimeGoals;
    private long homeTeamHalfTimeGoals;
    private long homeTeamShots;
    private long homeTeamShotsOnTarget;
    private long homeTeamFouls;
    private long homeTeamCorners;
    private long homeTeamYellowCards;
    private long homeTeamRedCards;
    private long awayTeamFullTimeGoals;
    private long awayTeamHalfTimeGoals;
    private long awayTeamShots;
    private long awayTeamShotsOnTarget;
    private long awayTeamFouls;
    private long awayTeamCorners;
    private long awayTeamYellowCards;
    private long awayTeamRedCards;
    private String referee;
    private ArrayList<Event> events;
    private ArrayList<Exhibition> exhibitions;

    public Match(String date, String homeTeam, String awayTeam, long homeTeamFullTimeGoals, long homeTeamHalfTimeGoals, long homeTeamShots, long homeTeamShotsOnTarget, long homeTeamFouls, long homeTeamCorners, long homeTeamYellowCards, long homeTeamRedCards, long awayTeamFullTimeGoals, long awayTeamHalfTimeGoals, long awayTeamShots, long awayTeamShotsOnTarget, long awayTeamFouls, long awayTeamCorners, long awayTeamYellowCards, long awayTeamRedCards, String referee, ArrayList<Event> events, ArrayList<Exhibition> exhibitions) {
        this.date = date;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamFullTimeGoals = homeTeamFullTimeGoals;
        this.homeTeamHalfTimeGoals = homeTeamHalfTimeGoals;
        this.homeTeamShots = homeTeamShots;
        this.homeTeamShotsOnTarget = homeTeamShotsOnTarget;
        this.homeTeamFouls = homeTeamFouls;
        this.homeTeamCorners = homeTeamCorners;
        this.homeTeamYellowCards = homeTeamYellowCards;
        this.homeTeamRedCards = homeTeamRedCards;
        this.awayTeamFullTimeGoals = awayTeamFullTimeGoals;
        this.awayTeamHalfTimeGoals = awayTeamHalfTimeGoals;
        this.awayTeamShots = awayTeamShots;
        this.awayTeamShotsOnTarget = awayTeamShotsOnTarget;
        this.awayTeamFouls = awayTeamFouls;
        this.awayTeamCorners = awayTeamCorners;
        this.awayTeamYellowCards = awayTeamYellowCards;
        this.awayTeamRedCards = awayTeamRedCards;
        this.referee = referee;
        this.events = events;
        this.exhibitions = exhibitions;
    }

    public String getDate() {
        return date;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public long getHomeTeamFullTimeGoals() {
        return homeTeamFullTimeGoals;
    }

    public long getHomeTeamHalfTimeGoals() {
        return homeTeamHalfTimeGoals;
    }

    public long getHomeTeamShots() {
        return homeTeamShots;
    }

    public long getHomeTeamShotsOnTarget() {
        return homeTeamShotsOnTarget;
    }

    public long getHomeTeamFouls() {
        return homeTeamFouls;
    }

    public long getHomeTeamCorners() {
        return homeTeamCorners;
    }

    public long getHomeTeamYellowCards() {
        return homeTeamYellowCards;
    }

    public long getHomeTeamRedCards() {
        return homeTeamRedCards;
    }

    public long getAwayTeamFullTimeGoals() {
        return awayTeamFullTimeGoals;
    }

    public long getAwayTeamHalfTimeGoals() {
        return awayTeamHalfTimeGoals;
    }

    public long getAwayTeamShots() {
        return awayTeamShots;
    }

    public long getAwayTeamShotsOnTarget() {
        return awayTeamShotsOnTarget;
    }

    public long getAwayTeamFouls() {
        return awayTeamFouls;
    }

    public long getAwayTeamCorners() {
        return awayTeamCorners;
    }

    public long getAwayTeamYellowCards() {
        return awayTeamYellowCards;
    }

    public long getAwayTeamRedCards() {
        return awayTeamRedCards;
    }

    public String getReferee() {
        return referee;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public ArrayList<Exhibition> getExhibitions() {
        return exhibitions;
    }
}
