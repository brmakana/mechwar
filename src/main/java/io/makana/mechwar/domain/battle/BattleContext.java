package io.makana.mechwar.domain.battle;

import io.makana.mechwar.domain.board.Hex;
import io.makana.mechwar.domain.players.Player;
import io.makana.mechwar.domain.players.PlayerClient;
import io.makana.mechwar.domain.units.Unit;
import io.makana.mechwar.domain.units.UnitState;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BattleContext {

    @Getter
    @NonNull
    private final Map<Unit, UnitState> unitStates;

    @Getter
    @NonNull
    private final Map<Player, List<UnitState>> unitsByPlayer;

    @Getter
    @NonNull
    private final List<Player> playersInOrderOfInitiative;

    @Getter
    @NonNull
    private final Map<Player, PlayerClient> playerClients;

    @Getter
    @NonNull private final Map<Hex, UnitState> unitsByHex;

    public BattleContext(@NonNull final Map<Player, List<UnitState>> unitStatesByPlayer,
                         @NonNull final List<Player> playersInOrderOfInitiative,
                         @NonNull final Map<Player, PlayerClient> playerClients) {
        if (unitStatesByPlayer.isEmpty()) {
            throw new IllegalArgumentException("No unit states per player!");
        }
        if (playersInOrderOfInitiative.isEmpty()) {
            throw new IllegalArgumentException("Players in order of initiative is empty!");
        }
        if (playerClients.isEmpty()) {
            throw new IllegalArgumentException("No player clients!");
        }
        final Map<Unit, UnitState> unitStates = new HashMap<>();
        final Map<Hex, UnitState> unitsByHex = new HashMap<>();
        unitStatesByPlayer.values()
                .stream()
                .flatMap(Collection::stream)
                .forEach(unitState -> {
                    unitStates.put(unitState.getUnit(), unitState);
                    unitsByHex.put(unitState.getLocation(), unitState);
                });
        this.unitStates = unitStates;
        this.unitsByHex = unitsByHex;
        this.unitsByPlayer = unitStatesByPlayer;
        this.playersInOrderOfInitiative = playersInOrderOfInitiative;
        this.playerClients = playerClients;
    }

    public PlayerClient getPlayerClient(final Player player) {
        return playerClients.get(player);
    }

    public UnitState getStateForUnit(final Unit unit) {
        return unitStates.get(unit);
    }

}
