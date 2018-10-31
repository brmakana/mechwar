package io.makana.mechwar.domain.events;

import io.makana.mechwar.domain.events.movement.GroundMovementType;
import io.makana.mechwar.domain.game.GameId;
import io.makana.mechwar.domain.player.Player;
import io.makana.mechwar.domain.units.GameUnitId;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MoveOrderRequest {
    private GameId gameId;
    private Player player;
    private int round;
    private GameUnitId unitToMove;
    private GroundMovementType movementType;
}
