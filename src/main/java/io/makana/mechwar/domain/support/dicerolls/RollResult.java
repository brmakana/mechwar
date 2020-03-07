package io.makana.mechwar.domain.support.dicerolls;

import io.makana.mechwar.domain.players.Player;
import lombok.Value;

@Value
public class RollResult {
    private final Player player;
    private final int roll;
}
