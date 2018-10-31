package io.makana.mechwar.engine.phases;

import io.makana.mechwar.domain.calculators.UnitRatioPerPlayerCalculator;
import io.makana.mechwar.domain.events.MoveOrderRequest;
import io.makana.mechwar.domain.events.movement.GroundMovementType;
import io.makana.mechwar.domain.game.GameId;
import io.makana.mechwar.domain.player.Player;
import io.makana.mechwar.domain.player.PlayerClient;
import io.makana.mechwar.domain.player.PlayerRepository;
import io.makana.mechwar.domain.player.Players;
import io.makana.mechwar.domain.units.GameUnitId;
import io.makana.mechwar.domain.units.GameUnitRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class GroundMovementPhaseTest {

    @MockBean
    private GameUnitRepository gameUnitRepository;

    @MockBean
    private PlayerRepository playerRepository;

    @MockBean
    private UnitRatioPerPlayerCalculator unitRatioPerPlayerCalculator;

    @MockBean
    private PlayerClient playerClient;

    @Autowired
    private GroundMovementPhase groundMovementPhase;

    @Test
    public void moveUnits() {
        Player attacker = new Player("player 1");
        Player defender = new Player("player 2");
        when(playerRepository.getPlayers(any(GameId.class))).thenReturn(new Players(new GameId(), attacker, defender));
        when(playerRepository.getClientForPlayer(any(Player.class))).thenReturn(playerClient);
        when(playerClient.requestGroundMovement(any(GameId.class), anyInt(), anySet(), anyInt())).thenReturn(getMoveOrderRequest(attacker, 1));
        when(gameUnitRepository.getUnitIdsForPlayer(any(GameId.class), eq(attacker))).thenReturn(new ArrayList<>());
        Map<Player, Integer> ratioMap = new HashMap<>();
        ratioMap.put(attacker, 1);
        ratioMap.put(defender, 1);
        when(unitRatioPerPlayerCalculator.calculateUnitRatios(any(Map.class))).thenReturn(ratioMap);

        groundMovementPhase.moveUnits(new GameId(), 1);
    }

    private MoveOrderRequest getMoveOrderRequest(Player player, int round) {
        MoveOrderRequest request = MoveOrderRequest.builder()
                .gameId(new GameId())
                .movementType(mock(GroundMovementType.class))
                .player(player)
                .round(round)
                .unitToMove(new GameUnitId())
                .build();
        return request;
    }

    @TestConfiguration
    public static class TestConfig {
        @Bean
        public GroundMovementPhase groundMovementPhase() {
            return new GroundMovementPhase();
        }
    }
}