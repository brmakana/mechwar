package io.makana.mechwar.domain.battle.phases.groundmovement;

import io.makana.mechwar.domain.battle.BattleContext;
import lombok.NonNull;
import lombok.Value;

@Value
public class GroundMovementPhaseResult {
    @NonNull
    private final BattleContext startingContext;
    @NonNull
    private final BattleContext endingContext;
}
