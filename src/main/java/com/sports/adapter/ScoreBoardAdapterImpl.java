package com.sports.adapter;

import com.sports.model.Game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ScoreBoardAdapterImpl implements IScoreBoardAdapter {

    private static ScoreBoardAdapterImpl INSTANCE;

    private final List<Game> scoreBoard = new ArrayList<>();

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
        scoreBoard.add(game);
        // At this moment returns the same as entry parameter because the persistence layer is just an in memory array
        return game;
    }

    @Override
    public List<Game> findAllOrderedByStartDate() {
        List<Game> auxList = new ArrayList<>(scoreBoard);
        auxList.sort(Comparator.comparing(Game::getStartDate));
        return auxList;
    }

    @Override
    public List<Game> findPlayingGamesByTeamNames(String... teamNames) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
