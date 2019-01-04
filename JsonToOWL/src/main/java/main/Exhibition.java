package main;

public class Exhibition {
    private String team;
    private String player;
    private int startMinute;
    private int endMinute;
    private String date;

    public Exhibition(String team, String player, int startMinute, int endMinute, String date) {
        this.team = team;
        this.player = player;
        this.startMinute = startMinute;
        this.endMinute = endMinute;
        this.date = date;
    }

    public String getTeam() {
        return team;
    }

    public String getPlayer() {
        return player;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public String getDate() {
        return date;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }
}
