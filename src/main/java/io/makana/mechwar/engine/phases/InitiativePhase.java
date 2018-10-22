package io.makana.mechwar.engine.phases;

import io.makana.mechwar.domain.events.InitiativePhaseResult;
import io.makana.mechwar.domain.game.GameId;
import io.makana.mechwar.domain.player.Player;
import io.makana.mechwar.domain.player.PlayerRepository;
import io.makana.mechwar.engine.Dice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class InitiativePhase {

    @Autowired
    private Dice dice;

    @Autowired
    private PlayerRepository playerRepository;

    public InitiativePhaseResult rollInitiative(GameId gameId) {
        log.info("Rolling for initiative");
        int max_roll_count = 10;
        int roll_count = 0;
        InitiativePhaseResult initiativePhaseResult = null;
        while (!isValid(initiativePhaseResult) && (roll_count < max_roll_count)) {
            initiativePhaseResult = rollForPlayers(gameId);
            roll_count++;
            if (!isValid(initiativePhaseResult)) {
                log.info("Players rolled a tie, rerolling");
            }
        }
        if (!isValid(initiativePhaseResult) && (roll_count >= max_roll_count)) {
            log.error("Re-rolled ties 10 times but still could not get a difference, aborting!");
            throw new RuntimeException("The universe has ended");
        }
        return initiativePhaseResult;
    }

    private boolean isValid(InitiativePhaseResult initiativePhaseResult) {
        if (initiativePhaseResult == null) {
            return false;
        }
        Set<Integer> rollResults = initiativePhaseResult.getInitiativeResults().values().stream().collect(Collectors.toSet());
        return (rollResults.size() >= 2);
    }

    private InitiativePhaseResult rollForPlayers(GameId gameId) {
        Map<Player, Integer> diceResult = new HashMap<>();
        Player initiativeWinner = null;
        int winningRoll = -1;
        for (Player player : getPlayers(gameId)) {
            int diceRoll = dice.roll2D6();
            diceResult.put(player, diceRoll);
            if (initiativeWinner == null) {
                initiativeWinner = player;
                winningRoll = diceRoll;
            } else {
                if (diceRoll > winningRoll) {
                    initiativeWinner = player;
                    winningRoll = diceRoll;
                }
            }
        }

        InitiativePhaseResult result = new InitiativePhaseResult(diceResult, initiativeWinner);
        return result;
    }

    private List<Player> getPlayers(GameId gameId) {
        return playerRepository.getPlayers(gameId);
    }
}
