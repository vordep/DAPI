package main;

public class Corner extends Event {
    private String team;
    private String player;

    public Corner(int id, int minute, String description, boolean followedBySetPiece, boolean followedByCorner, String date, String homeTeam, String awayTeam, String team, String player) {
        super(id, minute, description, followedBySetPiece, followedByCorner, date, homeTeam, awayTeam);
        this.team = team;
        this.player = player;
        this.eventType = "Corner";
    }

    public String getTeam() {
        return team;
    }

    public String getPlayer() {
        return player;
    }
}
