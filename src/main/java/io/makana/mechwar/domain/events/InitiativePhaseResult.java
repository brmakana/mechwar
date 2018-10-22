package io.makana.mechwar.domain.events;

import io.makana.mechwar.domain.player.Player;
import lombok.NonNull;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class InitiativePhaseResult implements GameEvent {

    private Map<Integer, Player> initiativeResults;

    public InitiativePhaseResult(@NonNull Map<Player, Integer> initiativeResults) {
        if (initiativeResults.size() != 2) {
            throw new IllegalStateException("Initiative results must be size 2!");
        }
        this.initiativeResults = new TreeMap<>();
        for (Map.Entry<Player, Integer> entry : initiativeResults.entrySet()) {
            this.initiativeResults.put(entry.getValue(), entry.getKey());
        }
    }

    public Player getWinner() {
        Iterator<Map.Entry<Integer, Player>> iterator = initiativeResults.entrySet().iterator();
        iterator.next();
        Player winner = iterator.next().getValue();
        return winner;
    }

    public Player getLoser() {
        Iterator<Map.Entry<Integer, Player>> iterator = initiativeResults.entrySet().iterator();
        Player loser = iterator.next().getValue();
        return loser;
    }

    public Map<Player, Integer> getInitiativeResults() {
        return this.initiativeResults.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getValue,
                Map.Entry::getKey
        ));
    }

}
