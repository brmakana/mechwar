package io.makana.mechwar.domain.battle;

import io.makana.mechwar.domain.players.Player;
import io.makana.mechwar.domain.players.PlayerClient;
import io.makana.mechwar.domain.units.UnitState;
import lombok.NonNull;
import lombok.Value;

import java.util.List;
import java.util.Map;

@Value
public class BattleContext {

    @NonNull private final Map<Player, List<UnitState>> unitsByPlayer;
    @NonNull private final List<Player> playersInOrderOfInitiative;
    @NonNull private final Map<Player, PlayerClient> playerClients;

    public PlayerClient getPlayerClient(final Player player) {
        return playerClients.get(player);
    }

}
