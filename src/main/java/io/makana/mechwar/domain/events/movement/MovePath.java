package io.makana.mechwar.domain.events.movement;

import io.makana.mechwar.domain.entities.board.Hex;
import io.makana.mechwar.domain.units.capabilities.movement.MovementCapability;
import lombok.Value;

@Value
public class MovePath {
    private MovementCapability movementType;
    private Hex targetHex;
}
