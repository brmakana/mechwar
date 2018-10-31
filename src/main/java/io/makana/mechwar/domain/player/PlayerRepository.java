package io.makana.mechwar.domain.player;

import io.makana.mechwar.domain.game.GameId;

public interface PlayerRepository {

    Players getPlayers(GameId gameId);

    PlayerClient getClientForPlayer(Player player);
}
