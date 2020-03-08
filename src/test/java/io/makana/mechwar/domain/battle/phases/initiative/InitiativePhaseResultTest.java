package io.makana.mechwar.domain.battle.phases.initiative;

import io.makana.mechwar.domain.battle.phases.initiative.InitiativePhaseResult;
import io.makana.mechwar.domain.entities.Team;
import io.makana.mechwar.domain.players.Player;
import io.makana.mechwar.domain.support.dicerolls.RollResult;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class InitiativePhaseResultTest {

    @Test
    public void sortsByScoreAscending() {
        final Map<Integer, Player> rollResults = new HashMap<>();
        final Player player1 = new Player("player 1", new Team("team 1"));
        final Player player2 = new Player("player 2", new Team("team 2"));
        final Player player3 = new Player("player 3", new Team("team 3"));
        rollResults.put(1, player1);
        rollResults.put(2, player2);
        rollResults.put(3, player3);

        final InitiativePhaseResult cut = new InitiativePhaseResult(rollResults);
        List<RollResult> initiativeResults = cut.getRollResults();
        assertEquals(3, initiativeResults.size());
        assertEquals(new RollResult(player1, 1), initiativeResults.get(0));
        assertEquals(new RollResult(player2, 2), initiativeResults.get(1));
        assertEquals(new RollResult(player3, 3), initiativeResults.get(2));
    }
}