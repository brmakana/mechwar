package io.makana.mechwar.domain.battle.phases.groundmovement.validators;

import io.makana.mechwar.domain.battle.BattleContext;
import io.makana.mechwar.domain.battle.phases.groundmovement.InvalidMoveOrder;
import io.makana.mechwar.domain.battle.phases.groundmovement.MoveOrderRequest;
import io.makana.mechwar.domain.battle.phases.groundmovement.Movement;
import io.makana.mechwar.domain.players.PlayerMovementRequest;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MoveOrderRequestValidator {

    public static void validate(@NonNull final BattleContext battleContext,
                                @NonNull final PlayerMovementRequest movementRequest,
                                @NonNull final MoveOrderRequest playerResponse) throws InvalidMoveOrder {
        try {
            checkOrThrow(!movementRequest.getPlayer().equals(playerResponse.getPlayer()),
                    "Players did not match!");
            checkOrThrow(!movementRequest.getUnitsAllowedToMove().contains(playerResponse.getUnitToMove()),
                    "Invalid unit for move order!");
            checkOrThrow(playerResponse.getDesiredMovement().isEmpty(),
                    "Empty movement list not allowed!");
            for (Movement movement : playerResponse.getDesiredMovement()) {
                /** @TODO validate:
                 *  1. unit can perform the movement type
                 *  2. unit is facing the right direction to perform the desired movement
                 *  3. unit has the movement points to perform the desired movement
                 *  4. No obstructions/other rules prevent it
                 */
            }
        } catch (final InvalidMoveOrder ex) {
            log.error("{} failed validation: {}", movementRequest, ex.getMessage(), ex);
            throw ex;
        }

    }

    private static void checkOrThrow(final boolean condition, final String message) {
        if (condition) {
            throw new InvalidMoveOrder(message);
        }
    }
}
