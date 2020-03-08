package io.makana.mechwar.domain.battle;

import io.makana.mechwar.domain.battle.phases.GroundMovementPhase;
import io.makana.mechwar.domain.battle.phases.InitiativePhase;
import io.makana.mechwar.domain.battle.phases.InitiativePhaseResult;
import io.makana.mechwar.domain.players.Player;
import io.makana.mechwar.domain.support.dicerolls.MaxRollsAttemptedException;
import io.makana.mechwar.domain.support.dicerolls.RollResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class Battle {

    @Autowired
    private final InitiativePhase initiativePhase;

    @Autowired
    private final GroundMovementPhase groundMovementPhase;

    private static final int MAX_TURNS = 100; // failsafe

    public BattleResult fight(final BattleInput battleInput) throws MaxRollsAttemptedException {
        int turn = 0;
        boolean isBattleFinished = false;
        while (!isBattleFinished && turn < MAX_TURNS) { // also check for turn limit?
            turn++;
            log.info("Starting turn {}", turn);
            // roll initiative
            final List<Player> players = battleInput.getPlayers();
            final InitiativePhaseResult initiativePhaseResult = initiativePhase.rollInitiative(players);
            log.debug("Initiative roll result for turn {}: {}", turn, initiativePhaseResult);
            final List<RollResult> initiativeRollResults = initiativePhaseResult.getRollResults();
            final List<Player> playersInOrderOfInitiative = initiativeRollResults
                    .stream()
                    .map(RollResult::getPlayer)
                    .collect(Collectors.toList());

            final BattleContext battleContext = new BattleContext(
                    battleInput.getUnitsByPlayer(),
                    playersInOrderOfInitiative,
                    battleInput.getPlayerClients());
            // ground movement
            groundMovementPhase.moveUnits(battleContext);
            // aerospace movement
            // weapon attack phase
            // physical attack phase
            // heat phase
            // end phase
            isBattleFinished = true;
        }
        return new BattleResult();
    }
}
