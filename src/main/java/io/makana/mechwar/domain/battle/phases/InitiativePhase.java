package io.makana.mechwar.domain.battle.phases;

import io.makana.mechwar.domain.support.dicerolls.Dice;
import io.makana.mechwar.domain.players.Player;
import io.makana.mechwar.domain.support.dicerolls.MaxRollsAttemptedException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class InitiativePhase {

    @Autowired
    private final Dice dice;

    public InitiativePhaseResult rollInitiative(final @NonNull List<Player> players) throws MaxRollsAttemptedException {
        log.info("Rolling for initiative");
        int rollCount = 0;
        final int maxRollCount = 100;

        while (rollCount < maxRollCount) {
            final Map<Integer, Player> rollResults = new HashMap<>();
            for (Player player : players) {
                final int roll = dice.roll2D6();
                rollResults.put(roll, player);
                log.debug("Player [{}] rolled a [{}]", player, roll);
            }
            if (rollResults.size() == players.size()) {
                return new InitiativePhaseResult(rollResults);
            } else {
                log.info("Tie roll, re-rolling");
            }
            rollCount++;
        }
        throw new MaxRollsAttemptedException("Unable to break ties in " + maxRollCount + " attempts");
    }
}
