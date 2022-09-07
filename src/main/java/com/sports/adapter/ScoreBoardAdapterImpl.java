package com.sports.adapter;

import com.sports.model.Game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * Reset the singleton instance (For testing purposes mainly).
     * Note: Cannot be accessed from outside package
     */
    public static void reset() {
        INSTANCE = null;
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
        // If the persistence layer were a database it should build a query with the name in OR clause
        return scoreBoard.stream().filter(game -> {
            List<String> auxTeamNames = List.of(teamNames);
            return (auxTeamNames.contains(game.getHomeTeam()) || auxTeamNames.contains(game.getAwayTeam()));
        }).collect(Collectors.toList());
    }

    @Override
    public void removeGame(String homeTeam, String awayTeam) {
        scoreBoard.removeIf(game -> homeTeam.equals(game.getHomeTeam()) && awayTeam.equals(game.getAwayTeam()));
    }

}
