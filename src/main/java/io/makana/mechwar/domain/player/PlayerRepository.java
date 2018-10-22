package io.makana.mechwar.domain.player;

import io.makana.mechwar.domain.game.GameId;

import java.util.List;

public interface PlayerRepository {

    List<Player> getPlayers(GameId gameId);
}
