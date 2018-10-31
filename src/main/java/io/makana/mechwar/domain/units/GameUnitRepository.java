package io.makana.mechwar.domain.units;

import io.makana.mechwar.domain.game.GameId;
import io.makana.mechwar.domain.player.Player;

import java.util.List;

public interface GameUnitRepository {

    List<GameUnitId> getUnitIdsForPlayer(GameId gameId, Player player);

    GameUnit getGameUnitById(GameUnitId unitToMove);
}
