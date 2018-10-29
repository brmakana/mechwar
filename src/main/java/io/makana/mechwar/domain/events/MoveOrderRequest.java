package io.makana.mechwar.domain.events;

import io.makana.mechwar.domain.game.GameId;
import io.makana.mechwar.domain.player.Player;
import lombok.Value;

@Value
public class MoveRequest {
    private GameId gameId;
    private Player player;
    private int round;
}
