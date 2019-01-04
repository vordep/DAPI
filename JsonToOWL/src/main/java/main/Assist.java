package main;

public class Assist extends Event {
    private String player;
    private String type;

    public Assist(int id, int minute, String description, boolean followedBySetPiece, boolean followedByCorner, String date, String homeTeam, String awayTeam, String player, String type) {
        super(id, minute, description, followedBySetPiece, followedByCorner, date, homeTeam, awayTeam);
        this.player = player;
        this.type = type;
        this.eventType = "Assist";
    }

    public String getPlayer() {
        return player;
    }

    public String getType() {
        return type;
    }
}
