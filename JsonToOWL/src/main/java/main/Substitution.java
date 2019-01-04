package main;

public class Substitution extends Event {
    private String team;
    private String replacingPlayer;
    private String replacedPlayer;
    private boolean injury;

    public Substitution(int id, int minute, String description, boolean followedBySetPiece, boolean followedByCorner, String date, String homeTeam, String awayTeam, String team, String replacingPlayer, String replacedPlayer, boolean injury) {
        super(id, minute, description, followedBySetPiece, followedByCorner, date, homeTeam, awayTeam);
        this.team = team;
        this.replacingPlayer = replacingPlayer;
        this.replacedPlayer = replacedPlayer;
        this.injury = injury;
        this.eventType = "Substitution";
    }

    public String getTeam() {
        return team;
    }

    public String getReplacingPlayer() {
        return replacingPlayer;
    }

    public String getReplacedPlayer() {
        return replacedPlayer;
    }

    public boolean isInjury() {
        return injury;
    }
}
