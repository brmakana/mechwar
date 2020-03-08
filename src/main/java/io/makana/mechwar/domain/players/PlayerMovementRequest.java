package io.makana.mechwar.domain.players;

import io.makana.mechwar.domain.units.Unit;
import lombok.Value;

import java.util.List;

@Value
public class PlayerMovementRequest {

    private final List<Unit> unitsAllowedToMove;
    private final int requiredToMoveCount;

}
