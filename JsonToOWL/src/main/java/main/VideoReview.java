package main;

public class VideoReview extends Event {
    private String event;
    private String result;

    public VideoReview(int id, int minute, String description, boolean followedBySetPiece, boolean followedByCorner, String date, String homeTeam, String awayTeam, String event, String result) {
        super(id, minute, description, followedBySetPiece, followedByCorner, date, homeTeam, awayTeam);
        this.event = event;
        this.result = result;
        this.eventType = "VideoReview";
    }

    public String getEvent() {
        return event;
    }

    public String getResult() {
        return result;
    }
}
