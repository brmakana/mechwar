package io.makana.mechwar.domain.battle.phases.groundmovement;

import io.makana.mechwar.domain.board.Hex;
import io.makana.mechwar.domain.board.HexFacing;
import io.makana.mechwar.domain.units.capabilities.movement.MovementMode;
import lombok.NonNull;
import lombok.Value;

@Value
public class Movement {
    @NonNull
    private MovementMode movementMode;
    @NonNull
    private Hex resultHex;
    @NonNull
    private HexFacing resultFacing;
}
