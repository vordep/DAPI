package main;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;

public class Parser {
    private static String month = null;
    private static String day = null;
    private static String endDay = null;

    private static final String URI = "http://www.semanticweb.org/dapi/ontologies/2018/11/football-statistics";
    private String path;
    private String code = "";

    private ArrayList<String> teams = new ArrayList<>();
    private ArrayList<String> players = new ArrayList<>();
    private ArrayList<Match> matches = new ArrayList<>();
    private ArrayList<Exhibition> exhibitions = new ArrayList<>();
    private ArrayList<Event> events = new ArrayList<>();

    public Parser(String path) {
        this.path = path;
    }

    public static void main(String[] args) {
        if(args.length < 1) {
            System.out.println("Please specify a path to a folder containing the matches' json files");
            return;
        }

        if(args.length >= 2) {
            month = args[1];
        }

        if(args.length == 3) {
            if(args[2].matches("..-..")) {
                day = args[2].split("-")[0];
                endDay = args[2].split("-")[1];
            } else {
                day = args[2];
            }
        }

        Parser parser = new Parser(args[0]);
        parser.parse();
        parser.generateCode();
    }

    public void parse() {
        File folder = new File(path);

        File[] files = folder.listFiles();

        JSONParser parser = new JSONParser();

        if(files != null) {
            for(File f: files) {
                if(month == null || (day == null && f.getName().matches("Game-..-" + month + "-.*")) || ((endDay == null) && f.getName().matches("Game-" + day + "-" + month + "-.*")) || (endDay != null && f.getName().split("-")[2].equals(month) && Integer.parseInt(f.getName().split("-")[1]) >= Integer.parseInt(day) && Integer.parseInt(f.getName().split("-")[1]) <= Integer.parseInt(endDay))) {
                    try {
                        JSONObject object = (JSONObject) parser.parse(new FileReader(f));
                        parseMatch(object);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void parseMatch(JSONObject object) {
        // General info
        String homeTeam = ((String) object.get("Home Team")).replace(" ", "_").replace("&", "&amp;");
        String awayTeam = ((String) object.get("Away Team")).replace(" ", "_").replace("&", "&amp;");
        String date = (String) object.get("Date");

        // Stats
        JSONObject stats = (JSONObject) object.get("Stats");
        long homeTeamFullTimeGoals = (Long) stats.get("Full Time Home Team Goals");
        long homeTeamHalfTimeGoals = (Long) stats.get("Half Time Home Team Goals");
        long homeTeamShots = (Long) stats.get("Home Team Shots");
        long homeTeamShotsOnTarget = (Long) stats.get("Home Team Shots on Target");
        long homeTeamFouls = (Long) stats.get("Home Team Fouls Committed");
        long homeTeamCorners = (Long) stats.get("Home Team Corners");
        long homeTeamYellowCards = (Long) stats.get("Home Team Yellow Cards");
        long homeTeamRedCards = (Long) stats.get("Home Team Red Cards");
        long awayTeamFullTimeGoals = (Long) stats.get("Full Time Away Team Goals");
        long awayTeamHalfTimeGoals = (Long) stats.get("Half Time Away Team Goals");
        long awayTeamShots = (Long) stats.get("Away Team Shots");
        long awayTeamShotsOnTarget = (Long) stats.get("Away Team Shots on Target");
        long awayTeamFouls = (Long) stats.get("Away Team Fouls Committed");
        long awayTeamCorners = (Long) stats.get("Away Team Corners");
        long awayTeamYellowCards = (Long) stats.get("Away Team Yellow Cards");
        long awayTeamRedCards = (Long) stats.get("Away Team Red Cards");
        String referee = (String) stats.get("Referee");

        // Lineups
        JSONObject lineups = (JSONObject) object.get("Lineup");
        ArrayList<Exhibition> matchExhibitions = new ArrayList<>();
        for(int i = 1; i <= 11; i++) {
            String homePlayer = ((String) lineups.get("Home Player " + i)).replace(" ", "_").replace("&", "&amp;");
            String awayPlayer = ((String) lineups.get("Away Player " + i)).replace(" ", "_").replace("&", "&amp;");

            if(!players.contains(homePlayer)) {
                players.add(homePlayer);
            }

            if(!players.contains(awayPlayer)) {
                players.add(awayPlayer);
            }

            Exhibition homeExhibition = new Exhibition(homeTeam, homePlayer, 0, 0, date);
            Exhibition awayExhibition = new Exhibition(awayTeam, awayPlayer, 0, 0, date);
            exhibitions.add(homeExhibition);
            exhibitions.add(awayExhibition);
            matchExhibitions.add(homeExhibition);
            matchExhibitions.add(awayExhibition);
        }

        // Commentary
        JSONArray commentary = (JSONArray) object.get("Commentary");
        ArrayList<Event> matchEvents = new ArrayList<>();

        int maxMinutes = 0;

        for(int i = 0; i < commentary.size(); i++) {
            JSONArray event = (JSONArray) commentary.get(i);
            String description = (String) event.get(2);
            String min = (String) event.get(58);
            int minute;
            try {
                minute = Integer.parseInt(min);
                maxMinutes = minute;
            } catch(Exception ex) {
                continue;
            }
            boolean followedBySetPiece = ((String)event.get(20)).equals("1");
            boolean followedByCorner = ((String)event.get(22)).equals("1");

            boolean isFoul = ((String)event.get(17)).equals("1");
            boolean isFreeKick = ((String)event.get(36)).equals("1");
            boolean isShot = ((String)event.get(8)).equals("1");
            boolean isOffside = ((String)event.get(23)).equals("1");
            boolean isCard = ((String)event.get(27)).equals("1");
            boolean isVideoReview = ((String)event.get(31)).equals("1");
            boolean isDelay = ((String)event.get(34)).equals("1");
            boolean isCorner = ((String)event.get(40)).equals("1");
            boolean isSubstitution = ((String)event.get(43)).equals("1");

            if(isShot) {
                String player = ((String) event.get(11)).replace(" ", "_").replace("&", "&amp;");
                String team = ((String) event.get(12)).replace(" ", "_").replace("&", "&amp;");
                String location = ((String) event.get(14));
                String netLocation = ((String) event.get(15));
                String result = ((String) event.get(10));
                String foot = ((String) event.get(13));
                boolean penalty = ((String)event.get(9)).equals("1");
                boolean hasAssist = !((String)event.get(16)).equals("NA");

                Assist assist = null;

                if(hasAssist) {
                    String assistPlayer = ((String) event.get(16)).replace(" ", "_").replace("&", "&amp;");
                    String type = ((String) event.get(21));

                    assist = new Assist(i, minute, description, followedBySetPiece, followedByCorner, date, homeTeam, awayTeam, assistPlayer, type);
                    events.add(assist);
                    matchEvents.add(assist);
                }

                Shot shot = new Shot(i, minute, description, followedBySetPiece, followedByCorner, date, homeTeam, awayTeam, player, team, penalty, location, netLocation, result, foot, assist);
                events.add(shot);
                matchEvents.add(shot);
            }

            if(isFoul) {
                String player = ((String) event.get(18)).replace(" ", "_").replace("&", "&amp;");
                String team = ((String) event.get(19)).replace(" ", "_").replace("&", "&amp;");

                boolean found = false;

                for(Event event1: matchEvents) {
                    if(event1.getMinute() == minute && event1 instanceof Foul) {
                        found = true;
                        ((Foul) event1).setCommittingPlayer(player);
                        ((Foul) event1).setCommittingTeam(team);
                    }
                }

                if(!found) {
                    Foul foul = new Foul(i, minute, description, followedBySetPiece, followedByCorner, date, homeTeam, awayTeam);
                    foul.setCommittingPlayer(player);
                    foul.setCommittingTeam(team);
                    events.add(foul);
                    matchEvents.add(foul);
                }
            }

            if(isFreeKick) {
                String player = ((String) event.get(37)).replace(" ", "_").replace("&", "&amp;");
                String team = ((String) event.get(38)).replace(" ", "_").replace("&", "&amp;");
                String freeKickLocation = ((String) event.get(39));
                boolean freeKickWon = ((String)event.get(36)).equals("1");

                boolean found = false;

                for(Event event1: matchEvents) {
                    if(event1.getMinute() == minute && event1 instanceof Foul) {
                        found = true;
                        ((Foul) event1).setWinningPlayer(player);
                        ((Foul) event1).setWinningTeam(team);
                        ((Foul) event1).setFreeKickLocation(freeKickLocation);
                        ((Foul) event1).setFreeKickWon(freeKickWon);
                    }
                }

                if(!found) {
                    Foul foul = new Foul(i, minute, description, followedBySetPiece, followedByCorner, date, homeTeam, awayTeam);
                    foul.setWinningPlayer(player);
                    foul.setWinningTeam(team);
                    foul.setFreeKickLocation(freeKickLocation);
                    foul.setFreeKickWon(freeKickWon);
                    events.add(foul);
                    matchEvents.add(foul);
                }
            }

            if(isOffside) {
                String player = ((String) event.get(25)).replace(" ", "_").replace("&", "&amp;");
                String team = ((String) event.get(24)).replace(" ", "_").replace("&", "&amp;");
                String passingPlayer = ((String) event.get(26)).replace(" ", "_").replace("&", "&amp;");

                Offside offside = new Offside(i, minute, description, followedBySetPiece, followedByCorner, date, homeTeam, awayTeam, team, player, passingPlayer);
                events.add(offside);
                matchEvents.add(offside);
            }

            if(isCard) {
                String player = ((String) event.get(29)).replace(" ", "_").replace("&", "&amp;");
                String team = ((String) event.get(30)).replace(" ", "_").replace("&", "&amp;");
                String type = ((String) event.get(28));

                Card card = new Card(i, minute, description, followedBySetPiece, followedByCorner, date, homeTeam, awayTeam, player, team, type);
                events.add(card);
                matchEvents.add(card);
            }

            if(isVideoReview) {
                String reviewEvent = ((String) event.get(32));
                String result = ((String) event.get(33));

                VideoReview videoReview = new VideoReview(i, minute, description, followedBySetPiece, followedByCorner, date, homeTeam, awayTeam, reviewEvent, result);
                events.add(videoReview);
                matchEvents.add(videoReview);
            }

            if(isDelay) {
                String team = ((String) event.get(35)).replace(" ", "_").replace("&", "&amp;");

                Delay delay = new Delay(i, minute, description, followedBySetPiece, followedByCorner, date, homeTeam, awayTeam, team);
                events.add(delay);
                matchEvents.add(delay);
            }

            if(isCorner) {
                String player = ((String) event.get(42)).replace(" ", "_").replace("&", "&amp;");
                String team = ((String) event.get(41)).replace(" ", "_").replace("&", "&amp;");

                Corner corner = new Corner(i, minute, description, followedBySetPiece, followedByCorner, date, homeTeam, awayTeam, team, player);
                events.add(corner);
                matchEvents.add(corner);
            }

            if(isSubstitution) {
                String replacedPlayer = ((String) event.get(47)).replace(" ", "_").replace("&", "&amp;");
                String replacingPlayer = ((String) event.get(46)).replace(" ", "_").replace("&", "&amp;");
                String team = ((String) event.get(45)).replace(" ", "_").replace("&", "&amp;");
                boolean injury = ((String)event.get(44)).equals("1");

                if(!players.contains(replacingPlayer)) {
                    players.add(replacingPlayer);
                }

                Exhibition exhibition = new Exhibition(team, replacingPlayer, minute, 0, date);
                exhibitions.add(exhibition);
                matchExhibitions.add(exhibition);

                for(Exhibition exhibition1: matchExhibitions) {
                    if(exhibition1.getPlayer().equals(replacedPlayer)) {
                        exhibition1.setEndMinute(minute);
                    }
                }

                Substitution substitution = new Substitution(i, minute, description, followedBySetPiece, followedByCorner, date, homeTeam, awayTeam, team, replacingPlayer, replacedPlayer, injury);
                events.add(substitution);
                matchEvents.add(substitution);
            }
        }

        if(maxMinutes == 0) {
            maxMinutes = 90;
        }

        for(Exhibition exhibition: matchExhibitions) {
            if(exhibition.getEndMinute() == 0) {
                exhibition.setEndMinute(maxMinutes);
            }
        }

        if(!teams.contains(homeTeam)) {
            teams.add(homeTeam);
        }

        if(!teams.contains(awayTeam)) {
            teams.add(awayTeam);
        }

        Match match = new Match(date, homeTeam, awayTeam, homeTeamFullTimeGoals, homeTeamHalfTimeGoals, homeTeamShots, homeTeamShotsOnTarget, homeTeamFouls, homeTeamCorners, homeTeamYellowCards, homeTeamRedCards, awayTeamFullTimeGoals, awayTeamHalfTimeGoals, awayTeamShots, awayTeamShotsOnTarget, awayTeamFouls, awayTeamCorners, awayTeamYellowCards, awayTeamRedCards, referee, matchEvents, matchExhibitions);
        matches.add(match);
    }

    public void generateCode() {
        for(String team: teams) {
            code += "<owl:NamedIndividual rdf:about=\"" + URI + "#" + team + "\">\n" +
                    "   <rdf:type rdf:resource=\"" + URI + "#Team\"/>\n" +
                    "   <hasTeamName>" + team.replace("_", " ") + "</hasTeamName>\n" +
                    "</owl:NamedIndividual>\n\n";
        }

        for(String player: players) {
            code += "<owl:NamedIndividual rdf:about=\"" + URI + "#" + player + "\">\n" +
                    "   <rdf:type rdf:resource=\"" + URI + "#Player\"/>\n" +
                    "   <hasPlayerName>" + player.replace("_", " ") + "</hasPlayerName>\n" +
                    "</owl:NamedIndividual>\n\n";
        }

        for(Exhibition exhibition: exhibitions) {
            code += "<owl:NamedIndividual rdf:about=\"" + URI + "#" + exhibition.getDate() + "_" + exhibition.getPlayer() + "\">\n" +
                    "   <rdf:type rdf:resource=\"" + URI + "#Exhibition\"/>\n" +
                    "   <hasTeam rdf:resource=\"http://www.semanticweb.org/dapi/ontologies/2018/11/football-statistics#" + exhibition.getTeam() + "\"/>\n" +
                    "   <hasPlayer rdf:resource=\"http://www.semanticweb.org/dapi/ontologies/2018/11/football-statistics#" + exhibition.getPlayer() + "\"/>\n" +
                    "   <hasStartMinute rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">" + exhibition.getStartMinute() + "</hasStartMinute>\n" +
                    "   <hasEndMinute rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">" + exhibition.getEndMinute() + "</hasEndMinute>\n" +
                    "</owl:NamedIndividual>\n\n";
        }

        for(Event event: events) {
            code += "<owl:NamedIndividual rdf:about=\"" + URI + "#" + event.getEventType() + "_" + event.getDate() + "_" + event.getHomeTeam() + "_" + event.getAwayTeam() + "_" + event.getId() + "\">\n" +
                    "   <rdf:type rdf:resource=\"" + URI + "#" + event.getEventType() + "\"/>\n" +
                    "   <occurredAtMinute rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">" + event.getMinute() + "</occurredAtMinute>\n" +
                    "   <hasDescription>" + event.getDescription() + "</hasDescription>\n" +
                    "   <wasFollowedBySetPiece rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\">" + event.isFollowedBySetPiece() + "</wasFollowedBySetPiece>\n" +
                    "   <wasFollowedByCorner rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\">" + event.isFollowedByCorner() + "</wasFollowedByCorner>\n";
            if(event instanceof Shot) {

                if(!((Shot) event).getPlayer().equals("NA")) {
                    code += "   <hasShootingPlayer rdf:resource=\"http://www.semanticweb.org/dapi/ontologies/2018/11/football-statistics#" + ((Shot) event).getPlayer() + "\"/>\n";
                }

                if(!((Shot) event).getTeam().equals("NA")) {
                    code += "   <hasShootingTeam rdf:resource=\"http://www.semanticweb.org/dapi/ontologies/2018/11/football-statistics#" + ((Shot) event).getTeam() + "\"/>\n";
                }

                code += "   <isPenaltyShot rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\">" + ((Shot) event).isPenalty() + "</isPenaltyShot>\n" +
                        "   <hasLocation>" + ((Shot) event).getLocation() + "</hasLocation>\n" +
                        "   <hasNetLocation>" + ((Shot) event).getNetLocation() + "</hasNetLocation>\n" +
                        "   <hasResult>" + ((Shot) event).getResult() + "</hasResult>\n" +
                        "   <withFoot>" + ((Shot) event).getFoot() + "</withFoot>\n";

                if(((Shot) event).getAssist() != null) {
                    code += "   <followedByAssist rdf:resource=\"http://www.semanticweb.org/dapi/ontologies/2018/11/football-statistics#Assist" + event.getDate() + "_" + event.getHomeTeam() + "_" + event.getAwayTeam() + "_" + event.getMinute() + "\"/>\n";
                }
            } else if(event instanceof Assist) {
                if(!((Assist) event).getPlayer().equals("NA")) {
                    code += "   <hasAssistingPlayer rdf:resource=\"http://www.semanticweb.org/dapi/ontologies/2018/11/football-statistics#" + ((Assist) event).getPlayer() + "\"/>\n";
                }

                code += "   <hasAssistType>" + ((Assist) event).getType() + "</hasAssistType>\n";
            } else if(event instanceof Offside) {
                if(!((Offside) event).getTeam().equals("NA")) {
                    code += "   <wasFromTeam rdf:resource=\"http://www.semanticweb.org/dapi/ontologies/2018/11/football-statistics#" + ((Offside) event).getTeam() + "\"/>\n";
                }

                if(!((Offside) event).getPlayer().equals("NA")) {
                    code += "   <hasOffsidePlayer rdf:resource=\"http://www.semanticweb.org/dapi/ontologies/2018/11/football-statistics#" + ((Offside) event).getPlayer() + "\"/>\n";
                }

                if(!((Offside) event).getPassingPlayer().equals("NA")) {
                    code += "   <wasFollowedByPassFrom rdf:resource=\"http://www.semanticweb.org/dapi/ontologies/2018/11/football-statistics#" + ((Offside) event).getPassingPlayer() + "\"/>\n";
                }
            } else if(event instanceof Card) {
                if(!((((Card) event).getTeam().equals("NA")))) {
                    code += "   <wasGivenToTeam rdf:resource=\"http://www.semanticweb.org/dapi/ontologies/2018/11/football-statistics#" + ((Card) event).getTeam() + "\"/>\n";
                }

                if(!((((Card) event).getPlayer().equals("NA")))) {
                    code += "   <wasGivenToPlayer rdf:resource=\"http://www.semanticweb.org/dapi/ontologies/2018/11/football-statistics#" + ((Card) event).getPlayer() + "\"/>\n";
                }

                 code += "   <hasCardType>" + ((Card) event).getType() + "</hasCardType>\n";
            } else if(event instanceof Foul) {
                if(!(((Foul) event).getWinningPlayer().equals("NA"))) {
                    code += "   <wasWonByPlayer rdf:resource=\"http://www.semanticweb.org/dapi/ontologies/2018/11/football-statistics#" + ((Foul) event).getWinningPlayer() + "\"/>\n";
                }

                if(!(((Foul) event).getWinningTeam().equals("NA"))) {
                    code += "   <wasWonByTeam rdf:resource=\"http://www.semanticweb.org/dapi/ontologies/2018/11/football-statistics#" + ((Foul) event).getWinningTeam() + "\"/>\n";
                }

                if(((Foul) event).getCommittingPlayer() != null && !(((Foul) event).getCommittingPlayer().equals("NA"))) {
                    code += "   <wasCommittedByPlayer rdf:resource=\"http://www.semanticweb.org/dapi/ontologies/2018/11/football-statistics#" + ((Foul) event).getCommittingPlayer() + "\"/>\n";
                }

                if(((Foul) event).getCommittingTeam() != null && !(((Foul) event).getCommittingTeam().equals("NA"))) {
                    code += "   <wasCommittedByTeam rdf:resource=\"http://www.semanticweb.org/dapi/ontologies/2018/11/football-statistics#" + ((Foul) event).getCommittingTeam() + "\"/>\n";
                }

                code += "   <hasFreeKickLocation>" + ((Foul) event).getFreeKickLocation() + "</hasFreeKickLocation>\n" +
                        "   <wasFreeKickWon rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\">" + ((Foul) event).isFreeKickWon() + "</wasFreeKickWon>\n";
            } else if(event instanceof VideoReview) {
                code += "   <hasVideoReviewEvent>" + ((VideoReview) event).getEvent() + "</hasVideoReviewEvent>\n" +
                        "   <hasVideoReviewResult>" + ((VideoReview) event).getResult() + "</hasVideoReviewResult>\n";
            } else if(event instanceof Delay) {
                code += "   <causedByTeam>" + ((Delay) event).getTeam() + "</causedByTeam>\n";
            } else if(event instanceof Corner) {
                if(!(((Corner) event).getPlayer().equals("NA"))) {
                    code += "   <concededByPlayer rdf:resource=\"http://www.semanticweb.org/dapi/ontologies/2018/11/football-statistics#" + ((Corner) event).getPlayer() + "\"/>\n";
                }

                if(!(((Corner) event).getTeam().equals("NA"))) {
                    code += "   <wonByTeam rdf:resource=\"http://www.semanticweb.org/dapi/ontologies/2018/11/football-statistics#" + ((Corner) event).getTeam() + "\"/>\n";
                }
            } else if(event instanceof Substitution) {
                if(!((Substitution) event).getReplacedPlayer().equals("NA")) {
                    code += "   <hasReplacedPlayer rdf:resource=\"http://www.semanticweb.org/dapi/ontologies/2018/11/football-statistics#" + ((Substitution) event).getReplacedPlayer() + "\"/>\n";
                }

                if(!((Substitution) event).getReplacingPlayer().equals("NA")) {
                    code += "   <hasEnteringPlayer rdf:resource=\"http://www.semanticweb.org/dapi/ontologies/2018/11/football-statistics#" + ((Substitution) event).getReplacingPlayer() + "\"/>\n";
                }

                if(!((Substitution) event).getTeam().equals("NA")) {
                    code += "   <wasPerformedByTeam rdf:resource=\"http://www.semanticweb.org/dapi/ontologies/2018/11/football-statistics#" + ((Substitution) event).getTeam() + "\"/>\n";
                }


                code += "   <becausePlayerInjured rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\">" + ((Substitution) event).isInjury() + "</becausePlayerInjured>\n";
            }


            code += "</owl:NamedIndividual>\n\n";
        }

        for(Match match: matches) {
            code += "<owl:NamedIndividual rdf:about=\"" + URI + "#" + match.getDate() + "_" + match.getHomeTeam() + "_" + match.getAwayTeam() + "\">\n" +
                    "   <rdf:type rdf:resource=\"" + URI + "#Match\"/>\n" +
                    "   <hasHomeTeam rdf:resource=\"http://www.semanticweb.org/dapi/ontologies/2018/11/football-statistics#" + match.getHomeTeam() + "\"/>\n" +
                    "   <hasAwayTeam rdf:resource=\"http://www.semanticweb.org/dapi/ontologies/2018/11/football-statistics#" + match.getAwayTeam() + "\"/>\n" +
                    "   <hasDate>" + match.getDate() + "</hasDate>\n" +
                    "   <hasReferee>" + match.getReferee() + "</hasReferee>\n" +
                    "   <hasHomeTeamFullTimeGoals rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">" + match.getHomeTeamFullTimeGoals() + "</hasHomeTeamFullTimeGoals>\n" +
                    "   <hasHomeTeamHalfTimeGoals rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">" + match.getHomeTeamHalfTimeGoals() + "</hasHomeTeamHalfTimeGoals>\n" +
                    "   <hasHomeTeamShots rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">" + match.getHomeTeamShots() + "</hasHomeTeamShots>\n" +
                    "   <hasHomeTeamShotsOnTarget rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">" + match.getHomeTeamShotsOnTarget() + "</hasHomeTeamShotsOnTarget>\n" +
                    "   <hasHomeTeamFouls rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">" + match.getHomeTeamFouls() + "</hasHomeTeamFouls>\n" +
                    "   <hasHomeTeamCorners rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">" + match.getHomeTeamCorners() + "</hasHomeTeamCorners>\n" +
                    "   <hasHomeTeamYellowCards rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">" + match.getHomeTeamYellowCards() + "</hasHomeTeamYellowCards>\n" +
                    "   <hasHomeTeamRedCards rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">" + match.getHomeTeamRedCards() + "</hasHomeTeamRedCards>\n" +
                    "   <hasAwayTeamFullTimeGoals rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">" + match.getAwayTeamFullTimeGoals() + "</hasAwayTeamFullTimeGoals>\n" +
                    "   <hasAwayTeamHalfTimeGoals rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">" + match.getAwayTeamHalfTimeGoals() + "</hasAwayTeamHalfTimeGoals>\n" +
                    "   <hasAwayTeamShots rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">" + match.getAwayTeamShots() + "</hasAwayTeamShots>\n" +
                    "   <hasAwayTeamShotsOnTarget rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">" + match.getAwayTeamShotsOnTarget() + "</hasAwayTeamShotsOnTarget>\n" +
                    "   <hasAwayTeamFouls rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">" + match.getAwayTeamFouls() + "</hasAwayTeamFouls>\n" +
                    "   <hasAwayTeamCorners rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">" + match.getAwayTeamCorners() + "</hasAwayTeamCorners>\n" +
                    "   <hasAwayTeamYellowCards rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">" + match.getAwayTeamYellowCards() + "</hasAwayTeamYellowCards>\n" +
                    "   <hasAwayTeamRedCards rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">" + match.getAwayTeamRedCards() + "</hasAwayTeamRedCards>\n";

            for(Exhibition exhibition: match.getExhibitions()) {
                code += "   <hasExhibition rdf:resource=\"http://www.semanticweb.org/dapi/ontologies/2018/11/football-statistics#" + exhibition.getDate() + "_" + exhibition.getPlayer() + "\"/>\n";
            }

            for(Event event: match.getEvents()) {
                code += "   <hasEvent rdf:resource=\"http://www.semanticweb.org/dapi/ontologies/2018/11/football-statistics#" + event.getEventType() + "_" + event.getDate() + "_" + event.getHomeTeam() + "_" + event.getAwayTeam() + "_" + event.getId() + "\"/>\n";
            }

            code += "</owl:NamedIndividual>\n\n";
        }

        try {
            PrintWriter out = new PrintWriter("output.xml");
            out.println(code);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
