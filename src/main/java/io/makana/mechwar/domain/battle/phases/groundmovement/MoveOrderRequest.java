package io.makana.mechwar.domain.battle.phases.groundmovement;

import io.makana.mechwar.domain.players.Player;
import io.makana.mechwar.domain.units.Unit;
import io.makana.mechwar.domain.units.capabilities.movement.MovementMode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.List;

/**
 * Models a <code>Player's</code> request for moving one unit.
 */
@Value
@Builder
public class MoveOrderRequest {
    @NonNull
    private Player player;
    @NonNull
    private Unit unitToMove;
    @NonNull
    @Singular("movement")
    private List<Movement> desiredMovement;
}
