package com.sports;

import com.sports.adapter.IScoreBoardAdapter;
import com.sports.error.GameNotFoundException;
import com.sports.error.TeamAlreadyPlayingException;
import com.sports.model.Game;

import java.time.LocalDateTime;
import java.util.Comparator;
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

    /**
     * Remove the game matching the given home and away teams
     *
     * @param homeTeam local team name
     * @param awayTeam visitor team name
     */
    public void finishGame(String homeTeam, String awayTeam) {
        List<Game> currentGames = scoreBoardAdapter.findPlayingGamesByTeamNames(homeTeam, awayTeam);
        if (currentGames.isEmpty()) {
            throw new GameNotFoundException(String.format("%s vs %s, ", homeTeam, awayTeam));
        }
        scoreBoardAdapter.removeGame(homeTeam, awayTeam);
    }

    /**
     * Get a summary of games by total score. Those games with the same total score will
     * be returned ordered by the most recently added to our system.
     *
     * @return a {@link List} af the games {@link Game} ordered by total score
     */
    public List<Game> getSummary() {
        List<Game> games = scoreBoardAdapter.findAllOrderedByStartDate();
        Comparator<Game> comparator = Comparator.comparing(game -> game.getHomeScore() + game.getAwayScore());
        comparator = comparator.thenComparing(Game::getStartDate).reversed();
        return games.stream().sorted(comparator).collect(Collectors.toList());
    }
}
