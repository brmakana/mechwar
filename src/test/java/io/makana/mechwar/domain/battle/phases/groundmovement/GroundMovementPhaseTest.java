package io.makana.mechwar.domain.battle.phases.groundmovement;

import io.makana.mechwar.domain.battle.BattleContext;
import io.makana.mechwar.domain.board.Hex;
import io.makana.mechwar.domain.board.HexFacing;
import io.makana.mechwar.domain.board.terrain.Clear;
import io.makana.mechwar.domain.board.terrain.Terrain;
import io.makana.mechwar.domain.entities.Team;
import io.makana.mechwar.domain.players.Player;
import io.makana.mechwar.domain.players.PlayerClient;
import io.makana.mechwar.domain.players.PlayerMovementRequest;
import io.makana.mechwar.domain.players.PlayerMovementResponse;
import io.makana.mechwar.domain.support.calculators.UnitRatioPerPlayerCalculator;
import io.makana.mechwar.domain.units.Unit;
import io.makana.mechwar.domain.units.UnitState;
import io.makana.mechwar.domain.units.capabilities.movement.Walk;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GroundMovementPhaseTest {

    @Mock
    private PlayerClient playerClient;

    private BattleContext battleContext;
    private List<Player> players;
    private Player player1, player2;
    private UnitState unit1, unit2;
    private Map<Player, List<UnitState>> unitStates;
    private GroundMovementPhase cut;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        players = getPlayers();
        player1 = players.get(0);
        player2 = players.get(1);
        unitStates = new HashMap<>();
        unitStates.put(player1, getUnits(1, player1));
        unit1 = unitStates.get(player1).get(0);
        unitStates.put(player2, getUnits(1, player2));
        unit2 = unitStates.get(player2).get(0);

        Map<Player, PlayerClient> playerClientMap = new HashMap<>();
        playerClientMap.put(player1, playerClient);
        playerClientMap.put(player2, playerClient);

        battleContext = new BattleContext(unitStates, players, playerClientMap);
        cut = new GroundMovementPhase(new UnitRatioPerPlayerCalculator());
    }

    @Test
    public void moveUnits() {
        final List<Movement> player1Movement = Arrays.asList(
                new Movement(Walk.getInstance(), unit1.getLocation(), HexFacing.NORTH)
        );
        final List<Movement> player2Movement = Arrays.asList(
                new Movement(Walk.getInstance(), unit2.getLocation(), HexFacing.NORTH)
        );
        final MoveOrderRequest player1Turn1MoveOrderRequest = new MoveOrderRequest(
                player1,
                unit1.getUnit(),
                player1Movement);

        final MoveOrderRequest player2Turn1MoveOrderRequest = new MoveOrderRequest(
                player2,
                unit2.getUnit(),
                player2Movement);

        final PlayerMovementRequest player1Turn1MovementRequest = new PlayerMovementRequest(
                player1,
                Arrays.asList(unit1.getUnit()),
                1);
        final PlayerMovementRequest player2Turn1MovementRequest = new PlayerMovementRequest(
                player2,
                Arrays.asList(unit2.getUnit()),
                1);

        when(playerClient.getMovementOrder(eq(player1Turn1MovementRequest)))
                .thenReturn(new PlayerMovementResponse(player1, Collections.singletonList(player1Turn1MoveOrderRequest)));

        when(playerClient.getMovementOrder(eq(player2Turn1MovementRequest)))
                .thenReturn(new PlayerMovementResponse(player2, Collections.singletonList(player2Turn1MoveOrderRequest)));


        final GroundMovementPhaseResult groundMovementPhaseResult = cut.moveUnits(this.battleContext);
        assertNotNull(groundMovementPhaseResult);
    }


    private List<UnitState> getUnits(final int unitCount, final Player player) {
        final List<UnitState> unitStates = new ArrayList<>();
        final Hex hex = new Hex(1, 0, new Clear(), new HashMap<>());
        for (int x = 0; x < unitCount; x++) {
            final Unit unit = new Unit();
            final UnitState unitState = new UnitState(player, unit, HexFacing.NORTH, hex);
            unitStates.add(unitState);
        }
        return unitStates;
    }

    private List<Player> getPlayers() {
        final Player player1 = new Player("player 1", new Team("team 1"));
        final Player player2 = new Player("player 2", new Team("team 2"));
        return Arrays.asList(player1, player2);
    }
}