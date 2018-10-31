package io.makana.mechwar.domain.events.movement.validation;

import io.makana.mechwar.domain.entities.Entity;
import io.makana.mechwar.domain.entities.EntityId;
import io.makana.mechwar.domain.entities.EntityRepository;
import io.makana.mechwar.domain.events.movement.MoveOrderRequest;
import io.makana.mechwar.domain.events.movement.MovePath;
import io.makana.mechwar.domain.units.GameUnit;
import io.makana.mechwar.domain.units.GameUnitId;
import io.makana.mechwar.domain.units.GameUnitRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validates a <code>{@link MoveOrderRequest}</code>
 */
@Component
@AllArgsConstructor
public class MoveOrderRequestValidator implements Validator {

    @Autowired
    @NonNull
    private GameUnitRepository gameUnitRepository;

    @Autowired
    @NonNull
    private EntityRepository entityRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return MoveOrderRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target == null || errors == null) {
            throw new IllegalArgumentException("Null arguments not allowed");
        }
        MoveOrderRequest request = (MoveOrderRequest) target;
        if (request.getGameId() == null) {
            errors.rejectValue("gameId", "invalidGameId", "GameId cannot be null!");
        }
        if (request.getPlayer() == null) {
            errors.rejectValue("player", "invalidPlayer", "Player cannot be null!");
        }
        if (request.getRound() <= 0) {
            errors.rejectValue("round", "invalidRound", "Round must be greater than zero");
        }
        if (request.getUnitToMove() == null) {
            errors.rejectValue("unitToMove", "invalidUnitToMove", "UnitToMove cannot be null!");
        }
        if (request.getMovePath() == null || request.getMovePath().isEmpty()) {
            errors.rejectValue("movePath", "invalidMovePath", "movePath cannot be null or empty!");
        }
        GameUnitId unitToMove = request.getUnitToMove();
        GameUnit targetUnit = gameUnitRepository.getGameUnitById(unitToMove);
        if (targetUnit == null) {
            errors.rejectValue("unitToMove", "unitDoesNotExist", "Unit [" + unitToMove + "] did not exist!");
        } else {
            EntityId targetUnitEntityId = targetUnit.getEntityId();
            if (targetUnitEntityId == null) {
                errors.rejectValue("unitToMove", "entityDoesNotExist", "Unit [" + unitToMove + "] had no entityId!");
            }
            Entity entity = entityRepository.getById(targetUnitEntityId);
            if (entity == null) {
                errors.rejectValue("unitToMove", "entityDoesNotExist", "Entity [" + targetUnitEntityId + "] does not exist!");
            }
            if (entity.getMovementCapabilities() == null || entity.getMovementCapabilities().isEmpty()) {
                errors.rejectValue("unitToMove", "entityDoesNotExist", "Entity [" + targetUnitEntityId + "] had no movement capabilities!");
            }
            /** @TODO this should move to a MovePathValidator **/
            for (MovePath movePath : ((MoveOrderRequest) target).getMovePath()) {
                if (!entity.hasMovementCapability(movePath.getMovementType())) {
                    errors.rejectValue("movePath",
                            "invalidMovementType",
                            "Entity [" + targetUnitEntityId + "] did not have movement capability [" + movePath.getMovementType() +"]");
                }
            }
        }
    }
}
