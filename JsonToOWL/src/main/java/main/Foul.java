package main;

public class Foul extends Event {
    private String committingPlayer;
    private String committingTeam;
    private String winningPlayer;
    private String winningTeam;
    private String freeKickLocation;
    private boolean freeKickWon;

    public Foul(int id, int minute, String description, boolean followedBySetPiece, boolean followedByCorner, String date, String homeTeam, String awayTeam) {
        super(id, minute, description, followedBySetPiece, followedByCorner, date, homeTeam, awayTeam);
        this.eventType = "Foul";
    }

    public String getCommittingPlayer() {
        return committingPlayer;
    }

    public String getCommittingTeam() {
        return committingTeam;
    }

    public String getWinningPlayer() {
        return winningPlayer;
    }

    public String getWinningTeam() {
        return winningTeam;
    }

    public String getFreeKickLocation() {
        return freeKickLocation;
    }

    public boolean isFreeKickWon() {
        return freeKickWon;
    }

    public void setCommittingPlayer(String committingPlayer) {
        this.committingPlayer = committingPlayer;
    }

    public void setCommittingTeam(String committingTeam) {
        this.committingTeam = committingTeam;
    }

    public void setWinningPlayer(String winningPlayer) {
        this.winningPlayer = winningPlayer;
    }

    public void setWinningTeam(String winningTeam) {
        this.winningTeam = winningTeam;
    }

    public void setFreeKickLocation(String freeKickLocation) {
        this.freeKickLocation = freeKickLocation;
    }

    public void setFreeKickWon(boolean freeKickWon) {
        this.freeKickWon = freeKickWon;
    }
}
