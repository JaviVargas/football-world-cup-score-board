package com.sports;

import com.sports.adapter.ScoreBoardAdapterImpl;
import com.sports.error.TeamAlreadyPlayingException;
import com.sports.model.Game;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScoreBoardTest {

    private static final String TEAM_NAME = "Team 1";
    private static final String ANOTHER_TEAM_NAME = "Team 2";
    private ScoreBoard scoreBoard;

    @Mock
    private Game game;
    @Mock
    private ScoreBoardAdapterImpl scoreBoardAdapter;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        scoreBoard = ScoreBoard.getInstance(scoreBoardAdapter);
    }

    @AfterEach
    void resetSingleton() {
        ScoreBoard.reset();
    }

    @Test
    void shouldCreateAGame() {
        scoreBoard.startGame(TEAM_NAME, ANOTHER_TEAM_NAME);

        ArgumentCaptor<Game> captor = ArgumentCaptor.forClass(Game.class);

        verify(scoreBoardAdapter).save(captor.capture());

        Game gameToSave = captor.getValue();
        assertEquals(TEAM_NAME, gameToSave.getHomeTeam());
        assertEquals(ANOTHER_TEAM_NAME, gameToSave.getAwayTeam());
        assertEquals(0, gameToSave.getHomeScore());
        assertEquals(0, gameToSave.getAwayScore());
        assertNotNull(gameToSave.getStartDate());
    }

    @Test
    void shouldNotCreateAGame() {
        when(scoreBoardAdapter.findPlayingGamesByTeamNames(TEAM_NAME, ANOTHER_TEAM_NAME)).thenReturn(List.of(game));
        assertThrows(TeamAlreadyPlayingException.class, () -> scoreBoard.startGame(TEAM_NAME, ANOTHER_TEAM_NAME));
        verify(scoreBoardAdapter, never()).save(game);
    }

    @Test
    void shouldFinishGame() {
        String homeTeam = "Home team 1";
        String awayTeam = "Away team 2";
        scoreBoard.finishGame(homeTeam, awayTeam);
        verify(scoreBoardAdapter).removeGame(homeTeam, awayTeam);
    }

}