package com.sports;

import com.sports.adapter.IScoreBoardAdapter;
import com.sports.error.TeamAlreadyPlayingException;
import com.sports.model.Game;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
     * Reset the singleton instance (For testing purposes mainly).
     * Note: Cannot be accessed from outside package
     */
    public static void reset() {
        INSTANCE = null;
    }

    /**
     * Create a new game with the given teams names
     *
     * @param homeTeam local team name
     * @param awayTeam visitor team name
     * @return the created game {@link Game}
     */
    public Game startGame(String homeTeam, String awayTeam) {
        List<Game> currentGames = scoreBoardAdapter.findPlayingGamesByTeamNames(homeTeam, awayTeam);
        if (!currentGames.isEmpty()) {
            throw new TeamAlreadyPlayingException(String.format("Following teams are already playing: %s",
                    currentGames.stream().map(g ->
                            String.format("%s vs %s, ", g.getHomeTeam(), g.getAwayTeam())).collect(Collectors.joining())));
        }
        Game game = new Game(homeTeam, awayTeam, 0, 0, LocalDateTime.now());
        return scoreBoardAdapter.save(game);
    }
}
