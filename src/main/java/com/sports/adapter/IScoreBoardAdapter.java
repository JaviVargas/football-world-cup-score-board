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
}
