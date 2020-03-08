package io.makana.mechwar.domain.battle.phases.groundmovement;

import io.makana.mechwar.domain.events.movement.MoveOrderRequest;
import io.makana.mechwar.domain.players.PlayerMovementRequest;

public interface MoveOrderRequestValidator {

    void isValid(final PlayerMovementRequest movementRequest,
                 final MoveOrderRequest moveOrderRequest) throws InvalidMoveOrder;

    String getDescription();
}
