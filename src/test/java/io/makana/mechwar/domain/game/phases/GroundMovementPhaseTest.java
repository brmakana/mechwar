package io.makana.mechwar.domain.game.phases;

import io.makana.mechwar.domain.calculators.UnitRatioPerPlayerCalculator;
import io.makana.mechwar.domain.entities.board.Hex;
import io.makana.mechwar.domain.entities.board.terrain.Clear;
import io.makana.mechwar.domain.events.movement.MoveOrderRequest;
import io.makana.mechwar.domain.events.movement.MovePath;
import io.makana.mechwar.domain.game.GameId;
import io.makana.mechwar.domain.player.Player;
import io.makana.mechwar.domain.player.PlayerClient;
import io.makana.mechwar.domain.player.PlayerRepository;
import io.makana.mechwar.domain.player.Players;
import io.makana.mechwar.domain.units.GameUnitId;
import io.makana.mechwar.domain.units.GameUnitRepository;
import io.makana.mechwar.domain.units.capabilities.movement.MovementCapability;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
        MovePath movePath = new MovePath(mock(MovementCapability.class), new Hex(0101, 1, Arrays.asList(new Clear())));
        MoveOrderRequest request = MoveOrderRequest.builder()
                .gameId(new GameId())
                .player(player)
                .round(round)
                .unitToMove(new GameUnitId())
                .addMovePath(movePath)
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