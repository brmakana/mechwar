package io.makana.mechwar.domain.events;

import io.makana.mechwar.domain.battle.phases.InitiativePhaseResult;
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
    public void sortsByScoreDescending() {
        final Map<Integer, Player> rollResults = new HashMap<>();
        final Player player1 = new Player("player 1", new Team("team 1"));
        final Player player2 = new Player("player 2", new Team("team 2"));
        rollResults.put(1, player1);
        rollResults.put(2, player2);

        final InitiativePhaseResult cut = new InitiativePhaseResult(rollResults);
        List<RollResult> initiativeResults = cut.getRollResults();
        assertEquals(2, initiativeResults.size());
        assertEquals(new RollResult(player2, 2), initiativeResults.get(0));
        assertEquals(new RollResult(player1, 1), initiativeResults.get(1));
    }
}