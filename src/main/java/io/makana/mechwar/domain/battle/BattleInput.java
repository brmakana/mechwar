package io.makana.mechwar.domain.battle;

import io.makana.mechwar.domain.players.Player;
import io.makana.mechwar.domain.players.PlayerClient;
import io.makana.mechwar.domain.units.Unit;
import io.makana.mechwar.domain.units.UnitState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Value
public class BattleInput {

    @NonNull private final Map<Player, List<UnitState>> unitsByPlayer;
    @NonNull private final Map<Player, PlayerClient> playerClients;

    public List<Player> getPlayers() {
        return new ArrayList<>(playerClients.keySet());
    }

    public List<UnitState> getUnits(Player player) {
        return unitsByPlayer.get(player);
    }

    public PlayerClient getPlayerClient(Player player) {
        return playerClients.get(player);
    }
}
