package io.makana.mechwar.domain.events;

import io.makana.mechwar.domain.player.Player;
import lombok.Value;

import java.util.Map;

@Value
public class InitiativePhaseResult implements GameEvent {

    private Map<Player, Integer> initiativeResults;
    private Player initiativeWinner;

}
