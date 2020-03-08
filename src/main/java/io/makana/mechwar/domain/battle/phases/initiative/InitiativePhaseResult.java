package io.makana.mechwar.domain.battle.phases.initiative;

import io.makana.mechwar.domain.players.Player;
import io.makana.mechwar.domain.support.dicerolls.CompareByScore;
import io.makana.mechwar.domain.support.dicerolls.RollResult;
import lombok.*;

import java.util.*;

@EqualsAndHashCode
@ToString
public class InitiativePhaseResult {

    @Getter
    private final List<RollResult> rollResults;

    public InitiativePhaseResult(@NonNull Map<Integer, Player> initiativeResults) {
        this.rollResults = new ArrayList<>(initiativeResults.size());
        for (Integer roll : initiativeResults.keySet()) {
            rollResults.add(new RollResult(initiativeResults.get(roll), roll));
        }
        rollResults.sort(new CompareByScore());
    }
}
