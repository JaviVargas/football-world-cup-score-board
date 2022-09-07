package com.sports.adapter;

import com.sports.model.Game;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ScoreBoardAdapterImplTest {

    private IScoreBoardAdapter scoreBoardAdapter;

    @BeforeEach
    void setup() {
        scoreBoardAdapter = ScoreBoardAdapterImpl.getInstance();
    }

    @AfterEach
    void reset() {
        ScoreBoardAdapterImpl.reset();
    }

    @Test
    void shouldSave() {
        Game game = new Game("Home team 1", "Away team 2", 0, 0, LocalDateTime.now());
        scoreBoardAdapter.save(game);
        List<Game> games = scoreBoardAdapter.findAllOrderedByStartDate();
        assertTrue(games.stream().anyMatch(g -> g.equals(game)));
    }

    @Test
    void shouldFindAllOrderedByStartDate() {
        saveGames();
        List<Game> gamesByDate = scoreBoardAdapter.findAllOrderedByStartDate();
        assertEquals(3, gamesByDate.size());
        assertEquals("Home team 5", gamesByDate.get(0).getHomeTeam());
        assertEquals("Home team 1", gamesByDate.get(1).getHomeTeam());
        assertEquals("Home team 3", gamesByDate.get(2).getHomeTeam());
    }

    @Test
    void shouldFindPlayingTeamByName() {
        saveGames();
        List<Game> playingGames = scoreBoardAdapter.findPlayingGamesByTeamNames("Home team 3", "Away team 2");
        assertEquals(2, playingGames.size());
        List<Game> anotherPlayingGames = scoreBoardAdapter.findPlayingGamesByTeamNames("Home team 3", "Away team 4");
        assertEquals(1, anotherPlayingGames.size());
    }

    @Test
    void shouldFindGameByTeamsNames() {
        saveGames();
        Optional<Game> gameOpt = scoreBoardAdapter.findGameByTeams("Home team 1", "Away team 2");
        assertTrue(gameOpt.isPresent());
        Game game = gameOpt.get();
        assertEquals("Home team 1", game.getHomeTeam());
        assertEquals("Away team 2", game.getAwayTeam());
        assertEquals(0, game.getHomeScore());
        assertEquals(0, game.getAwayScore());
        assertNotNull(game.getStartDate());
    }

    @Test
    void shouldRemoveGame() {
        saveGames();
        String homeTeam = "Home team 1";
        String awayTeam = "Away team 2";
        scoreBoardAdapter.removeGame(homeTeam, awayTeam);
        List<Game> games = scoreBoardAdapter.findPlayingGamesByTeamNames(homeTeam, awayTeam);
        assertTrue(games.stream().noneMatch(game -> homeTeam.equals(game.getHomeTeam())));
    }

    private void saveGames() {
        Game game = new Game("Home team 1", "Away team 2", 0, 0, LocalDateTime.now());
        Game anotherGame = new Game("Home team 3", "Away team 4", 0, 0, LocalDateTime.now().plusHours(2));
        Game anotherOneGame = new Game("Home team 5", "Away team 6", 0, 0, LocalDateTime.now().minusHours(5));
        scoreBoardAdapter.save(game);
        scoreBoardAdapter.save(anotherGame);
        scoreBoardAdapter.save(anotherOneGame);
    }
}