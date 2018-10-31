package io.makana.mechwar.domain.player;

import io.makana.mechwar.domain.events.movement.MoveOrderRequest;
import io.makana.mechwar.domain.game.GameId;
import io.makana.mechwar.domain.units.GameUnitId;

import java.util.Set;

public interface PlayerClient {
    MoveOrderRequest requestGroundMovement(GameId gameId, int round, Set<GameUnitId> availableToMove, int maxAllowedToMove);
}
