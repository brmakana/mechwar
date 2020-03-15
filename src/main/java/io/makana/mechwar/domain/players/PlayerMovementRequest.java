package io.makana.mechwar.domain.players;

import io.makana.mechwar.domain.units.Unit;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
public class PlayerMovementRequest {
    @NonNull
    private final Player player;
    @NonNull
    private final List<Unit> unitsAllowedToMove;
    @NonNull
    private final int requiredToMoveCount;
}
