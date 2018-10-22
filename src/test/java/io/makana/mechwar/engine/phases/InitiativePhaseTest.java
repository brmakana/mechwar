package io.makana.mechwar.engine.phases;

import io.makana.mechwar.domain.events.InitiativePhaseResult;
import io.makana.mechwar.domain.game.GameId;
import io.makana.mechwar.domain.player.Player;
import io.makana.mechwar.domain.player.PlayerRepository;
import io.makana.mechwar.engine.Dice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InitiativePhaseTest {

    @MockBean
    private PlayerRepository playerRepository;

    @MockBean
    private Dice dice;

    @Autowired
    private InitiativePhase initiativePhase;

    @Test
    public void tracks_players_and_initiative() {
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");

        when(playerRepository.getPlayers(any(GameId.class))).thenReturn(Arrays.asList(player1, player2));
        when(dice.roll2D6()).thenReturn(1).thenReturn(2);

        Map<Player, Integer> expectedRoll = new HashMap<>();
        expectedRoll.put(player1, 1);
        expectedRoll.put(player2, 2);

        InitiativePhaseResult actual = initiativePhase.rollInitiative(new GameId());
        assertEquals(expectedRoll, actual.getInitiativeResults());
        assertEquals(player1, actual.getLoser());
        assertEquals(player2, actual.getWinner());
    }

    @Test
    public void rerolls_when_ties() {
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");

        when(playerRepository.getPlayers(any(GameId.class))).thenReturn(Arrays.asList(player1, player2));
        when(dice.roll2D6()).thenReturn(1).thenReturn(1).thenReturn(1).thenReturn(1).thenReturn(3).thenReturn(4);

        Map<Player, Integer> expectedRoll = new HashMap<>();
        expectedRoll.put(player1, 3);
        expectedRoll.put(player2, 4);

        InitiativePhaseResult actual = initiativePhase.rollInitiative(new GameId());
        assertEquals(expectedRoll, actual.getInitiativeResults());
        assertEquals(player1, actual.getLoser());
        assertEquals(player2, actual.getWinner());
    }


    @TestConfiguration
    public static class TestConfig {
        @Bean
        public InitiativePhase initiativePhase() {
            return new InitiativePhase();
        }
    }
}