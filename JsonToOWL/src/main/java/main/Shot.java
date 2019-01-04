package main;

public class Shot extends Event {
    private String player;
    private String team;
    private boolean penalty;
    private String location;
    private String netLocation;
    private String result;
    private String foot;
    private Assist assist;

    public Shot(int id, int minute, String description, boolean followedBySetPiece, boolean followedByCorner, String date, String homeTeam, String awayTeam, String player, String team, boolean penalty, String location, String netLocation, String result, String foot, Assist assist) {
        super(id, minute, description, followedBySetPiece, followedByCorner, date, homeTeam, awayTeam);
        this.player = player;
        this.team = team;
        this.penalty = penalty;
        this.location = location;
        this.netLocation = netLocation;
        this.result = result;
        this.foot = foot;
        this.assist = assist;
        this.eventType = "Shot";
    }

    public String getPlayer() {
        return player;
    }

    public String getTeam() {
        return team;
    }

    public boolean isPenalty() {
        return penalty;
    }

    public String getLocation() {
        return location;
    }

    public String getNetLocation() {
        return netLocation;
    }

    public String getResult() {
        return result;
    }

    public String getFoot() {
        return foot;
    }

    public Assist getAssist() {
        return assist;
    }
}
