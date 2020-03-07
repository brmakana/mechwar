package io.makana.mechwar.domain.players;

import io.makana.mechwar.domain.events.movement.MoveOrderRequest;
import io.makana.mechwar.domain.units.Unit;

import java.util.Map;

public class PlayerMovementResponse {
    public Map<Unit, MoveOrderRequest> getUnitMoveOrders() {
        throw new UnsupportedOperationException("not yet implemented");
    }
}
