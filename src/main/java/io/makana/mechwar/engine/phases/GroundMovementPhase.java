package io.makana.mechwar.engine.phases;

import io.makana.mechwar.domain.game.GameId;
import io.makana.mechwar.domain.player.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class MovementPhase {

    @Autowired
    private PlayerRepository playerRepository;

    public void moveUnits(GameId gameId, int round) {

    }
}
