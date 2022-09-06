package com.sports.model;

import java.util.Date;
import java.util.Objects;

public class Game {

    private String homeTeam;

    private String awayTeam;

    private int homeScore;

    private int awayScore;

    private Date startDate;

    public Game(String homeTeam, String awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public Game(String homeTeam, String awayTeam, int homeScore, int awayScore, Date startDate, Date endDate) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.startDate = startDate;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return homeScore == game.homeScore && awayScore == game.awayScore && homeTeam.equals(game.homeTeam)
                && awayTeam.equals(game.awayTeam) && startDate.equals(game.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homeTeam, awayTeam, homeScore, awayScore, startDate);
    }

    @Override
    public String toString() {
        return "{"
                + "\"homeTeam\":\"" + homeTeam + "\""
                + ", \"awayTeam\":\"" + awayTeam + "\""
                + ", \"homeScore\":\"" + homeScore + "\""
                + ", \"awayScore\":\"" + awayScore + "\""
                + ", \"startDate\":" + startDate
                + "}";
    }
}
