package io.makana.mechwar.domain.battle.phases.groundmovement;

import io.makana.mechwar.domain.events.movement.MoveOrderRequest;
import io.makana.mechwar.domain.players.PlayerMovementRequest;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class DisallowNullOrEmptyMoveOrderRequests implements MoveOrderRequestValidator {

    private static final String DESCRIPTION = "Validates that a MoveOrderRequest has non-null and non-empty move orders";

    @Override
    public void isValid(@NonNull final PlayerMovementRequest playerMovementRequest,
                           @NonNull final MoveOrderRequest moveOrderRequest) throws InvalidMoveOrder {
        if (moveOrderRequest.getUnitToMove() == null ||
                moveOrderRequest.getMovePath() == null ||
                moveOrderRequest.getMovePath().isEmpty()) {
            throw new InvalidMoveOrder("Null or empty moveOrderRequest elements not allowed");
        }
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }


}
