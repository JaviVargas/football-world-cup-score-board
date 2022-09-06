package com.sports.adapter;

import com.sports.model.Game;

public interface IScoreBoardAdapter {

    /**
     * Save a new game.
     *
     * @param game the game to be saved.
     * @return saved {@link Game}.
     */
    Game save(Game game);
}
