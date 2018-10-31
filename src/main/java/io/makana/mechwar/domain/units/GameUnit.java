package io.makana.mechwar.domain.units;

import io.makana.mechwar.domain.entities.EntityId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * <code>{@link GameUnit}</code> models a single, concrete instance of a unit in the game.
 */
@Data
@AllArgsConstructor
@Builder
public class GameUnit {

    @NonNull
    private GameUnitId gameUnitId;

    @NonNull
    private EntityId entityId;
}
