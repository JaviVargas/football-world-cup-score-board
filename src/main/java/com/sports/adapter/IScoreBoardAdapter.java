package com.sports.adapter;

import com.sports.model.Game;

import java.util.List;

public interface IScoreBoardAdapter {

    /**
     * Save a new game.
     *
     * @param game the game to be saved.
     * @return saved {@link Game}.
     */
    Game save(Game game);

    /**
     * Find games by the teams name if they are playing.
     *
     * @param teamNames the name/s of the team/s.
     * @return a {@link List} containing the {@link Game} matching the given names, otherwise an empty List.
     */
    List<Game> findPlayingGamesByTeamNames(String... teamNames);

    /**
     * Find all saved games ordered by start date.
     *
     * @return a {@link List<Game>} with all stored games ordered by start date.
     */
    List<Game> findAllOrderedByStartDate();

    /**
     * Remove the game matching the given home and away teams
     *
     * @param homeTeam local team name
     * @param awayTeam visitor team name
     */
    void removeGame(String homeTeam, String awayTeam);
}
