package io.makana.mechwar.domain.units;

import io.makana.mechwar.domain.entities.EntityId;
import lombok.*;

/**
 * <code>{@link Unit}</code> models a single, concrete instance of a unit in the game.
 */
@EqualsAndHashCode
@ToString
public class Unit {

    @Getter
    private final UnitId unitId;

    public Unit() {
        unitId = new UnitId();
    }
}
