package com.sports.adapter;

import com.sports.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScoreBoardAdapterImplTest {

    private IScoreBoardAdapter scoreBoardAdapter;

    @BeforeEach
    void setup() {
        scoreBoardAdapter = ScoreBoardAdapterImpl.getInstance();
    }

    @Test
    void shouldSave() {
        Game game = new Game("Home team", "Away team", 0, 0, new Date(), null);
        scoreBoardAdapter.save(game);
        List<Game> games = scoreBoardAdapter.findAllOrderedByStartDate();
        assertTrue(games.stream().anyMatch(g -> g.equals(game)));
    }
}