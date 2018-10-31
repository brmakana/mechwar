package io.makana.mechwar.domain.calculators;

import io.makana.mechwar.domain.player.Player;
import io.makana.mechwar.domain.units.GameUnitId;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Calculates the "unit ratio" for each player based on their unit counts. This is used by phases when one player
 * has more or less units than another. For example, if Player 1 has 2 units and Player 2 has 1, Player 1 will have a ratio of 2:1
 */
@Slf4j
public class UnitRatioPerPlayerCalculator {

    public Map<Player, Integer> calculateUnitRatios(@NonNull Map<Player, List<GameUnitId>> unitsByPlayer) {
        if (unitsByPlayer.isEmpty()) {
            throw new IllegalArgumentException("Argument cannot be empty!");
        }
        Map<Player, Integer> unitCountsByPlayer = unitsByPlayer.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> e.getValue().size()
        ));
        List<Integer> unitCounts = unitCountsByPlayer.values().stream().sorted().collect(Collectors.toList());
        if (unitCounts.get(0) == 0) {
            throw new IllegalArgumentException("Players cannot have zero units!");
        }
        int smallest = unitCounts.get(0);
        Map<Player, Integer> results = unitCountsByPlayer.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> Math.max(1, (int)Math.ceil(((double)entry.getValue() / smallest)))
        ));
        log.info("Calculated unit ratios: {}", results);
        return results;
    }
}
