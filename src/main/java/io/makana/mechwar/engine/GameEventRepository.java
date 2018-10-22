package io.makana.mechwar.engine;

import io.makana.mechwar.domain.events.GameEvent;
import io.makana.mechwar.domain.game.GameId;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class GameEventRepository {

    private Map<GameId, Map<Integer, List<GameEvent>>> gameEventsByRound;

    public GameEventRepository() {
        gameEventsByRound = new HashMap<>();
    }

    public void saveEvent(@NonNull GameId game, int round, @NonNull GameEvent gameEvent) {
        if (log.isDebugEnabled()) {
            log.debug("Saving {} for game {} and round {}", gameEvent, game, round);
        }
        Map<Integer, List<GameEvent>> eventsByRound = gameEventsByRound.computeIfAbsent(game, m -> new HashMap<>());
        List<GameEvent> roundEvents = eventsByRound.computeIfAbsent(round, i -> new ArrayList<>());
        roundEvents.add(gameEvent);
        eventsByRound.put(round, roundEvents);
        gameEventsByRound.put(game, eventsByRound);
    }


}
