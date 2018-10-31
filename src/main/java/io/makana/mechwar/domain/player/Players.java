package io.makana.mechwar.domain.player;

import io.makana.mechwar.domain.game.GameId;
import lombok.NonNull;
import lombok.Value;

import java.util.Arrays;
import java.util.List;

/**
 * Represents a collection of <code>{@link Player}s</code> in the game. Useful over a generic collection
 * as there are a fixed number of players.
 */
@Value
public class Players {

    @NonNull
    private GameId gameId;
    @NonNull
    private Player attacker;
    @NonNull
    private Player defender;

    public List<Player> getPlayers() {
        return Arrays.asList(attacker, defender);
    }

}
