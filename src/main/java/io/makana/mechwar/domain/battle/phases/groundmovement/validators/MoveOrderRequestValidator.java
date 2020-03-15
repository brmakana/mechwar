package io.makana.mechwar.domain.battle.phases.groundmovement.validators;

import io.makana.mechwar.domain.battle.phases.groundmovement.InvalidMoveOrder;
import io.makana.mechwar.domain.battle.phases.groundmovement.MoveOrderRequest;
import io.makana.mechwar.domain.players.PlayerMovementRequest;
import lombok.NonNull;

public class MoveOrderRequestValidator {

    public static void validate(@NonNull final PlayerMovementRequest movementRequest,
                        @NonNull final MoveOrderRequest playerResponse) throws InvalidMoveOrder {
        if (!movementRequest.getPlayer().equals(playerResponse.getPlayer())) {
            throw new InvalidMoveOrder("Players did not match!");
        }
        if (!movementRequest.getUnitsAllowedToMove().contains(playerResponse.getUnitToMove())) {
            throw new InvalidMoveOrder("Invalid unit for move order!");
        }
        if (playerResponse.getDesiredMovement().isEmpty()) {
            throw new InvalidMoveOrder("Empty movement list not allowed!");
        }

    }
    
}
