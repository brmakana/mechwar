package io.makana.mechwar.domain.battle.phases.groundmovement.validators;

import io.makana.mechwar.domain.battle.BattleContext;
import io.makana.mechwar.domain.battle.phases.groundmovement.InvalidMoveOrder;
import io.makana.mechwar.domain.battle.phases.groundmovement.MoveOrderRequest;
import io.makana.mechwar.domain.battle.phases.groundmovement.Movement;
import io.makana.mechwar.domain.players.PlayerMovementRequest;
import lombok.NonNull;

public class MoveOrderRequestValidator {

    public static void validate(@NonNull final BattleContext battleContext,
                                @NonNull final PlayerMovementRequest movementRequest,
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
        for (Movement movement : playerResponse.getDesiredMovement()) {
            /** @TODO validate:
             *  1. unit can perform the movement type
             *  2. unit is facing the right direction to perform the desired movement
             *  3. unit has the movement points to perform the desired movement
             *  4. No obstructions/other rules prevent it
             */
        }

    }

}
