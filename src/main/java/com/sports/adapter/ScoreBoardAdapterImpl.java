package com.sports.adapter;

import com.sports.model.Game;

public class ScoreBoardAdapterImpl implements IScoreBoardAdapter {

    private static ScoreBoardAdapterImpl INSTANCE;

    private ScoreBoardAdapterImpl() {
    }

    public static ScoreBoardAdapterImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ScoreBoardAdapterImpl();
        }
        return INSTANCE;
    }

    @Override
    public Game save(Game game) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
