package io.makana.mechwar.engine.phases;

import io.makana.mechwar.domain.events.InitiativePhaseResult;
import io.makana.mechwar.domain.game.GameId;
import io.makana.mechwar.domain.player.Player;
import io.makana.mechwar.domain.player.PlayerRepository;
import io.makana.mechwar.domain.player.Players;
import io.makana.mechwar.engine.Dice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class InitiativePhase {

    @Autowired
    private Dice dice;

    @Autowired
    private PlayerRepository playerRepository;

    public InitiativePhaseResult rollInitiative(GameId gameId) {
        log.info("Rolling for initiative");
        int roll_count = 0;
        int max_roll_count = 10;
        InitiativePhaseResult initiativePhaseResult = null;
        while (!isValid(initiativePhaseResult) && (roll_count < max_roll_count)) {
            initiativePhaseResult = rollForPlayers(gameId);
            roll_count++;
            if (!isValid(initiativePhaseResult)) {
                log.info("Players rolled a tie, rerolling");
            }
        }
        if (!isValid(initiativePhaseResult) && (roll_count >= max_roll_count)) {
            throw new IllegalStateException("Unable to break ties after 10 tries");
        }
        return initiativePhaseResult;
    }

    private boolean isValid(InitiativePhaseResult initiativePhaseResult) {
        if (initiativePhaseResult == null) {
            return false;
        }
        Set<Integer> rollResults = initiativePhaseResult.getInitiativeResults().values().stream().collect(Collectors.toSet());
        return (rollResults.size() >= 2 && rollResults.size() == initiativePhaseResult.getInitiativeResults().size());
    }

    private InitiativePhaseResult rollForPlayers(GameId gameId) {
        Map<Player, Integer> diceResultMap = new HashMap<>();
        List<Player> players = getPlayers(gameId).getPlayers();
        if (players == null || players.isEmpty() || players.size() != 2) {
            throw new IllegalStateException("Players list was not size 2");
        }
        for (Player player : players) {
            int diceRoll = dice.roll2D6();
            diceResultMap.put(player, diceRoll);
        }

        InitiativePhaseResult result = new InitiativePhaseResult(diceResultMap);
        return result;
}

    private Players getPlayers(GameId gameId) {
        return playerRepository.getPlayers(gameId);
    }

    public Dice getDice() {
        return dice;
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }

    public PlayerRepository getPlayerRepository() {
        return playerRepository;
    }

    public void setPlayerRepository(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }
}
