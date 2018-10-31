package io.makana.mechwar.engine;

import io.makana.mechwar.domain.events.InitiativePhaseResult;
import io.makana.mechwar.domain.game.GameId;
import io.makana.mechwar.domain.player.PlayerRepository;
import io.makana.mechwar.engine.phases.InitiativePhase;
import org.springframework.beans.factory.annotation.Autowired;

public class GameController {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private InitiativePhase initiativePhase;

    @Autowired
    private GameEventRepository gameEventRepository;

    public void playRound(GameId gameId, int round) {
        // roll initiative
        InitiativePhaseResult initiativePhaseResult = initiativePhase.rollInitiative(gameId);
        gameEventRepository.saveEvent(gameId, round, initiativePhaseResult);
        // ground movement
        // air movement
        // air attack
        //
    }
}
