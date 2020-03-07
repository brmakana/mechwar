package io.makana.mechwar.domain.battle;

import io.makana.mechwar.domain.battle.phases.InitiativePhase;
import io.makana.mechwar.domain.battle.phases.InitiativePhaseResult;
import io.makana.mechwar.domain.entities.EntityId;
import io.makana.mechwar.domain.entities.Team;
import io.makana.mechwar.domain.events.movement.MoveOrderRequest;
import io.makana.mechwar.domain.players.Player;
import io.makana.mechwar.domain.players.PlayerClient;
import io.makana.mechwar.domain.players.PlayerMovementRequest;
import io.makana.mechwar.domain.players.PlayerMovementResponse;
import io.makana.mechwar.domain.support.dicerolls.MaxRollsAttemptedException;
import io.makana.mechwar.domain.units.GameUnitId;
import io.makana.mechwar.domain.units.Unit;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BattleTest {

    @Mock
    private InitiativePhase initiativePhase;
    @Mock
    private PlayerClient playerClient;
    @Mock
    private BattleInput battleInput;
    @Mock
    private PlayerMovementResponse playerMovementResponse;

    private List<Player> players;

    @Before
    public void setup() throws MaxRollsAttemptedException {
        MockitoAnnotations.initMocks(this);
        players = getPlayers();
        when(initiativePhase.rollInitiative(eq(players))).thenReturn(getInitiativePhaseResult(players));
        when(battleInput.getPlayers()).thenReturn(players);
        when(battleInput.getPlayerClient(any())).thenReturn(playerClient);
        when(battleInput.getUnits(any())).thenReturn(getUnits(1));
        when(playerClient.getMovementOrder(any(PlayerMovementRequest.class))).thenReturn(playerMovementResponse);
    }

    @Test
    public void fight() throws MaxRollsAttemptedException {
        final Map<Unit, MoveOrderRequest> moveOrders = new HashMap<>();

        when(playerMovementResponse.getUnitMoveOrders()).thenReturn(moveOrders);
        Battle battle = new Battle(initiativePhase);
        final BattleResult battleResult = battle.fight(battleInput);
    }

    private InitiativePhaseResult getInitiativePhaseResult(final List<Player> players) {
        final Map<Integer, Player> rollResults = new HashMap<>();
        for (int x = 0; x < players.size(); x++) {
            rollResults.put(x, players.get(x));
        }
        final InitiativePhaseResult result = new InitiativePhaseResult(rollResults);
        return result;
    }

    private List<Unit> getUnits(int unitCount) {
        List<Unit> units = new ArrayList<>();
        for (int x = 0; x < unitCount; x++) {
            final Unit unit = new Unit(new GameUnitId(), new EntityId());
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