package main;

public class Event {
    private int minute;
    private String description;
    private boolean followedBySetPiece;
    private boolean followedByCorner;
    private String date;
    private String homeTeam;
    private String awayTeam;
    protected String eventType = "Event";
    private int id;

    public Event(int id, int minute, String description, boolean followedBySetPiece, boolean followedByCorner, String date, String homeTeam, String awayTeam) {
        this.id = id;
        this.minute = minute;
        this.description = description;
        this.followedBySetPiece = followedBySetPiece;
        this.followedByCorner = followedByCorner;
        this.date = date;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public int getId() {
        return id;
    }

    public int getMinute() {
        return minute;
    }

    public String getDescription() {
        return description;
    }

    public boolean isFollowedBySetPiece() {
        return followedBySetPiece;
    }

    public boolean isFollowedByCorner() {
        return followedByCorner;
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

    public String getEventType() {
        return eventType;
    }
}
