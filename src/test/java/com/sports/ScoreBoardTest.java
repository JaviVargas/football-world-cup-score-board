package com.sports;

import com.sports.adapter.ScoreBoardAdapterImpl;
import com.sports.error.GameNotFoundException;
import com.sports.error.TeamAlreadyPlayingException;
import com.sports.model.Game;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        when(scoreBoardAdapter.findGameByTeams(TEAM_NAME, ANOTHER_TEAM_NAME)).thenReturn(Optional.of(game));
        scoreBoard.finishGame(TEAM_NAME, ANOTHER_TEAM_NAME);
        verify(scoreBoardAdapter).removeGame(TEAM_NAME, ANOTHER_TEAM_NAME);
    }

    @Test
    void shouldNotFinishGame() {
        when(scoreBoardAdapter.findPlayingGamesByTeamNames(TEAM_NAME, ANOTHER_TEAM_NAME)).thenReturn(Collections.emptyList());
        assertThrows(GameNotFoundException.class, () -> scoreBoard.finishGame(TEAM_NAME, ANOTHER_TEAM_NAME));
        verify(scoreBoardAdapter, never()).removeGame(TEAM_NAME, ANOTHER_TEAM_NAME);
    }

    @Test
    void shouldUpdate() {
        when(scoreBoardAdapter.findGameByTeams(TEAM_NAME, ANOTHER_TEAM_NAME)).thenReturn(Optional.of(game));
        scoreBoard.updateScore(TEAM_NAME, ANOTHER_TEAM_NAME, 2, 1);
        verify(scoreBoardAdapter).updateScore(TEAM_NAME, ANOTHER_TEAM_NAME, 2, 1);
    }

    @Test
    void shouldNotUpdate() {
        when(scoreBoardAdapter.findGameByTeams(TEAM_NAME, ANOTHER_TEAM_NAME)).thenReturn(Optional.empty());
        assertThrows(GameNotFoundException.class, () -> scoreBoard.updateScore(TEAM_NAME, ANOTHER_TEAM_NAME, 2, 1));
        verify(scoreBoardAdapter, never()).updateScore(TEAM_NAME, ANOTHER_TEAM_NAME, 2, 1);
    }

    @Test
    void shouldGetSummary() {
        Game gameA = new Game("Mexico", "Canada", 0, 5, LocalDateTime.now().minusHours(4));
        Game gameB = new Game("Spain", "Brazil", 10, 2, LocalDateTime.now().minusHours(3));
        Game gameC = new Game("Germany", "France", 2, 2, LocalDateTime.now().minusHours(2));
        Game gameD = new Game("Uruguay", "Italy", 6, 6, LocalDateTime.now().minusHours(1));
        Game gameE = new Game("Argentina", "Australia", 3, 1, LocalDateTime.now());

        when(scoreBoardAdapter.findAllOrderedByStartDate()).thenReturn(List.of(gameA, gameB, gameC, gameD, gameE));
        List<Game> summary = scoreBoard.getSummary();

        verify(scoreBoardAdapter).findAllOrderedByStartDate();
        assertEquals(5, summary.size());
        assertEquals(gameD, summary.get(0));
        assertEquals(gameB, summary.get(1));
        assertEquals(gameA, summary.get(2));
        assertEquals(gameE, summary.get(3));
        assertEquals(gameC, summary.get(4));
    }

}