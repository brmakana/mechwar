package io.makana.mechwar.domain.battle.phases;

import io.makana.mechwar.domain.battle.BattleContext;
import io.makana.mechwar.domain.events.movement.MoveOrderRequest;
import io.makana.mechwar.domain.players.Player;
import io.makana.mechwar.domain.players.PlayerClient;
import io.makana.mechwar.domain.players.PlayerMovementRequest;
import io.makana.mechwar.domain.players.PlayerMovementResponse;
import io.makana.mechwar.domain.support.calculators.UnitRatioPerPlayerCalculator;
import io.makana.mechwar.domain.units.Unit;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class GroundMovementPhase {

    private final UnitRatioPerPlayerCalculator unitRatioPerPlayerCalculator;

    public GroundMovementPhaseResult moveUnits(final BattleContext battleContext) {
        log.info("Beginning ground movement phase");
        Map<Player, List<Unit>> unitsToMovePerPlayer = battleContext.getUnitsByPlayer();
        Map<Player, Integer> playerUnitCountRatios = calculatePlayerUnitCountRatios(unitsToMovePerPlayer);
        boolean isMovementComplete = unitsToMovePerPlayer.isEmpty();
        while (!isMovementComplete) {
            log.debug("Units to move: {}", unitsToMovePerPlayer);
            for (Player player : battleContext.getPlayersInOrderOfInitiative()) {
                log.debug("On {}", player);
                if (unitsToMovePerPlayer.containsKey(player)) {
                    final PlayerClient playerClient = battleContext.getPlayerClient(player);
                    final List<Unit> unitsAllowedToMove = unitsToMovePerPlayer.get(player);
                    final PlayerMovementRequest playerMovementRequest = new PlayerMovementRequest(
                            unitsAllowedToMove,
                            playerUnitCountRatios.get(player)
                    );
                    log.debug("Sending {} to {}", playerMovementRequest, player);
                    final PlayerMovementResponse playerMovementResponse = playerClient.getMovementOrder(playerMovementRequest);
                    log.info("Received {} movement response from {}", playerMovementResponse, player);
                    final List<MoveOrderRequest> unitMoveOrders = playerMovementResponse.getMoveOrderRequests();
                    /** @TODO validate movement orders **/
                    if (playerMovementResponse == null || playerMovementResponse.getMoveOrderRequests() == null) {
                        throw new IllegalArgumentException("Cannot have null/empty move order requests in response");
                    }
                    for (MoveOrderRequest moveOrderRequest : playerMovementResponse.getMoveOrderRequests()) {
                        if (!moveOrderRequest.getPlayer().equals(player)) {
                            throw new IllegalArgumentException("Invalid player for move order!");
                        }
                        if (!unitsAllowedToMove.contains(moveOrderRequest.getUnitToMove())) {
                            throw new IllegalArgumentException("Invalid unit for move order!");
                        }
                    }
                    /** @TODO apply movement orders **/
                    updateAvailableUnitsToMove(unitsToMovePerPlayer, player, unitsAllowedToMove, unitMoveOrders);
                    /** @TODO update ratio **/
                    if (!unitsToMovePerPlayer.isEmpty()) {
                        playerUnitCountRatios = calculatePlayerUnitCountRatios(unitsToMovePerPlayer);
                    }
                } else {
                    log.debug("Player [{}] had no more available units to move, skipping", player);
                }
            }
            log.debug("Units to move per player size: {}", unitsToMovePerPlayer.size());
            isMovementComplete = unitsToMovePerPlayer.isEmpty();
            if (isMovementComplete) {
                log.info("All units have moved");
            }
        }
        return null; /** @TODO return results of ground movement **/
    }

    private void updateAvailableUnitsToMove(Map<Player, List<Unit>> unitsToMovePerPlayer,
                                            Player player,
                                            List<Unit> unitsAllowedToMove,
                                            List<MoveOrderRequest> unitMoveOrders) {
        List<Unit> unitsMoved = unitMoveOrders.stream()
                .map(MoveOrderRequest::getUnitToMove)
                .collect(Collectors.toList());
        log.debug("These units moved and cannot move again this turn: {}", unitsMoved);
        List<Unit> newUnitList = new ArrayList<>();
        newUnitList.addAll(unitsAllowedToMove);
        newUnitList.removeAll(unitsMoved);
        if (newUnitList.isEmpty()) {
            unitsToMovePerPlayer.remove(player);
        } else {
            unitsToMovePerPlayer.put(player, newUnitList);
        }
    }

    private Map<Player, Integer> calculatePlayerUnitCountRatios(Map<Player, List<Unit>> unitsToMovePerPlayer) {
        Map<Player, Integer> ratios = unitRatioPerPlayerCalculator.calculateUnitRatios(unitsToMovePerPlayer);
        log.debug("New player/unit ratios calculated: {}", ratios);
        return ratios;
    }
}
