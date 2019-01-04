package main;

public class Card extends Event {
    private String player;
    private String team;
    private String type;

    public Card(int id, int minute, String description, boolean followedBySetPiece, boolean followedByCorner, String date, String homeTeam, String awayTeam, String player, String team, String type) {
        super(id, minute, description, followedBySetPiece, followedByCorner, date, homeTeam, awayTeam);
        this.player = player;
        this.team = team;
        this.type = type;
        this.eventType = "Card";
    }

    public String getPlayer() {
        return player;
    }

    public String getTeam() {
        return team;
    }

    public String getType() {
        return type;
    }
}
