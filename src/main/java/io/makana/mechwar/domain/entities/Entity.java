package io.makana.mechwar.domain.entities;

import io.makana.mechwar.domain.units.capabilities.movement.MovementCapability;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.Set;

@Value
public class Entity {
    private EntityId entityId;

    @Singular
    private Set<MovementCapability> movementCapabilities;

    public boolean hasMovementCapability(@NonNull MovementCapability toCheck) {
        if (this.movementCapabilities == null || movementCapabilities.isEmpty()) {
            return false;
        }
        for (MovementCapability capability : movementCapabilities) {
            if (capability.getClass().equals(toCheck.getClass())) {
                return true;
            }
        }
        return false;
    }
}
