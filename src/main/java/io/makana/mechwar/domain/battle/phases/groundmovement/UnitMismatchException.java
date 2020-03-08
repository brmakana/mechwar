package io.makana.mechwar.domain.battle.phases.groundmovement;

import io.makana.mechwar.domain.events.movement.MoveOrderRequest;
import io.makana.mechwar.domain.players.PlayerMovementRequest;
import org.springframework.stereotype.Component;

@Component
public class UnitMismatchException implements MoveOrderRequestValidator {

    private static final String DESCRIPTION = "Validates that player and unit(s) were valid";

    @Override
    public void isValid(final PlayerMovementRequest movementRequest,
                           final MoveOrderRequest moveOrderRequest) throws InvalidMoveOrder {
        if (!movementRequest.getUnitsAllowedToMove().contains(moveOrderRequest.getUnitToMove())) {
            throw new InvalidMoveOrder("Invalid unit for move order!");
        }

    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
