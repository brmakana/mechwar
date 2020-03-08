package io.makana.mechwar.domain.battle;

import io.makana.mechwar.domain.players.Player;
import io.makana.mechwar.domain.players.PlayerClient;
import io.makana.mechwar.domain.units.Unit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.List;
import java.util.Map;

@Value
public class BattleContext {

    private final Map<Player, List<Unit>> unitsByPlayer;
    private final List<Player> playersInOrderOfInitiative;
    private final Map<Player, PlayerClient> playerClients;

    public PlayerClient getPlayerClient(final Player player) {
        return playerClients.get(player);
    }

}
