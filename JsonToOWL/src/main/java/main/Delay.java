package main;

public class Delay extends Event {
    private String team;

    public Delay(int id, int minute, String description, boolean followedBySetPiece, boolean followedByCorner, String date, String homeTeam, String awayTeam, String team) {
        super(id, minute, description, followedBySetPiece, followedByCorner, date, homeTeam, awayTeam);
        this.team = team;
        this.eventType = "Delay";
    }

    public String getTeam() {
        return team;
    }
}
