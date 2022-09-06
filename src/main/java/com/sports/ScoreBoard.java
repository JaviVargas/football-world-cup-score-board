package com.sports;

import com.sports.adapter.IScoreBoardAdapter;
import com.sports.model.Game;

import java.util.Date;

public class ScoreBoard {

    private static ScoreBoard INSTANCE;

    private final IScoreBoardAdapter scoreBoardAdapter;

    private ScoreBoard(IScoreBoardAdapter scoreBoardAdapter) {
        this.scoreBoardAdapter = scoreBoardAdapter;
    }

    /**
     * Return a ScoreBoard as a singleton
     *
     * @param scoreBoardAdapter the implementation to use
     * @return the score board instance
     */
    public static ScoreBoard getInstance(IScoreBoardAdapter scoreBoardAdapter) {
        if (INSTANCE == null) {
            INSTANCE = new ScoreBoard(scoreBoardAdapter);
        }
        return INSTANCE;
    }

    /**
     * Create a new game with the given teams names
     *
     * @param homeTeam local team name
     * @param awayTeam visitor team name
     * @return the created game {@link Game}
     */
    public Game startGame(String homeTeam, String awayTeam) {
        // TODO add validation: if team is already playing throw error
        Game game = new Game(homeTeam, awayTeam, 0, 0, new Date(), null);
        return scoreBoardAdapter.save(game);
    }
}
