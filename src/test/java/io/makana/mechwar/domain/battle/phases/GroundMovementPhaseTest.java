package io.makana.mechwar.domain.battle.phases;

import io.makana.mechwar.domain.battle.Battle;
import io.makana.mechwar.domain.battle.BattleContext;
import io.makana.mechwar.domain.battle.BattleInput;
import io.makana.mechwar.domain.battle.BattleResult;
import io.makana.mechwar.domain.entities.Team;
import io.makana.mechwar.domain.events.movement.MoveOrderRequest;
import io.makana.mechwar.domain.players.Player;
import io.makana.mechwar.domain.players.PlayerClient;
import io.makana.mechwar.domain.players.PlayerMovementRequest;
import io.makana.mechwar.domain.players.PlayerMovementResponse;
import io.makana.mechwar.domain.support.calculators.UnitRatioPerPlayerCalculator;
import io.makana.mechwar.domain.support.dicerolls.MaxRollsAttemptedException;
import io.makana.mechwar.domain.units.Unit;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class GroundMovementPhaseTest {

    @Mock
    private PlayerClient playerClient;

    private BattleContext battleContext;
    private List<Player> players;
    private List<Unit> units;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        players = getPlayers();
        units = getUnits(2);
        Map<Player, List<Unit>> unitsByPlayer = new HashMap<>();
        unitsByPlayer.put(players.get(0), units.subList(0, 1));
        unitsByPlayer.put(players.get(1), units.subList(1, 2));

        Map<Player, PlayerClient> playerClientMap = new HashMap<>();
        playerClientMap.put(players.get(0), playerClient);
        playerClientMap.put(players.get(1), playerClient);

        battleContext = new BattleContext(unitsByPlayer, players, playerClientMap);
    }

    @Test
    public void moveUnits() {
        final MoveOrderRequest player1Turn1MoveOrderRequest = new MoveOrderRequest(
                players.get(0),
                units.get(0),
                new ArrayList<>());

        final MoveOrderRequest player2Turn1MoveOrderRequest = new MoveOrderRequest(
                players.get(1),
                units.get(1),
                new ArrayList<>());

        final PlayerMovementRequest player1Turn1MovementRequest = new PlayerMovementRequest(units.subList(0, 1), 1);
        final PlayerMovementRequest player2Turn1MovementRequest = new PlayerMovementRequest(units.subList(1, 2), 1);

        when(playerClient.getMovementOrder(eq(player1Turn1MovementRequest)))
                .thenReturn(new PlayerMovementResponse(Collections.singletonList(player1Turn1MoveOrderRequest)));

        when(playerClient.getMovementOrder(eq(player2Turn1MovementRequest)))
                .thenReturn(new PlayerMovementResponse(Collections.singletonList(player2Turn1MoveOrderRequest)));

        final GroundMovementPhase cut = new GroundMovementPhase(new UnitRatioPerPlayerCalculator());
        GroundMovementPhaseResult groundMovementPhaseResult = cut.moveUnits(this.battleContext);
    }


    private List<Unit> getUnits(int unitCount) {
        final List<Unit> units = new ArrayList<>();
        for (int x = 0; x < unitCount; x++) {
            final Unit unit = new Unit();
            units.add(unit);
        }
        return units;
    }

    private List<Player> getPlayers() {
        final Player player1 = new Player("player 1", new Team("team 1"));
        final Player player2 = new Player("player 2", new Team("team 2"));
        return Arrays.asList(player1, player2);
    }
}