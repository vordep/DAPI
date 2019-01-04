package main;

public class Offside extends Event {
    private String team;
    private String player;
    private String passingPlayer;

    public Offside(int id, int minute, String description, boolean followedBySetPiece, boolean followedByCorner, String date, String homeTeam, String awayTeam, String team, String player, String passingPlayer) {
        super(id, minute, description, followedBySetPiece, followedByCorner, date, homeTeam, awayTeam);
        this.team = team;
        this.player = player;
        this.passingPlayer = passingPlayer;
        this.eventType = "Offside";
    }

    public String getTeam() {
        return team;
    }

    public String getPlayer() {
        return player;
    }

    public String getPassingPlayer() {
        return passingPlayer;
    }
}
