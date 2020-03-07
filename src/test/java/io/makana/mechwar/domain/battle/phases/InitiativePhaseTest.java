package io.makana.mechwar.domain.battle.phases;

import io.makana.mechwar.domain.players.Player;
import io.makana.mechwar.domain.entities.Team;
import io.makana.mechwar.domain.support.dicerolls.Dice;
import io.makana.mechwar.domain.support.dicerolls.MaxRollsAttemptedException;
import io.makana.mechwar.domain.support.dicerolls.RollResult;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class InitiativePhaseTest {

    @Mock
    private Dice dice;

    private final Player player1 = new Player("player1", new Team("team 1"));
    private final Player player2 = new Player("player2", new Team("team 2"));
    private InitiativePhase cut;
    private final List<Player> players = Collections.unmodifiableList(Arrays.asList(player1, player2));

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        cut = new InitiativePhase(dice);
    }

    @Test(expected = MaxRollsAttemptedException.class)
    public void throwsExceptionOnTooManyTieRolls() throws Exception {
        when(dice.roll2D6()).thenReturn(1);
        cut.rollInitiative(players);
    }

    @Test
    public void rerollsOnTies() throws Exception {
        when(dice.roll2D6())
                .thenReturn(1).thenReturn(1) // first round is a tie
                .thenReturn(1).thenReturn(2);

        final InitiativePhaseResult initiativePhaseResult = cut.rollInitiative(Arrays.asList(player1, player2));
        List<RollResult> results = initiativePhaseResult.getRollResults();
        assertEquals(2, results.size());
        assertEquals(new RollResult(player2, 2), results.get(0));
        assertEquals(new RollResult(player1, 1), results.get(1));
    }
}