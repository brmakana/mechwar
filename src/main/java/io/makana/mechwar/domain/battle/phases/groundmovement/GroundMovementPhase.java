package io.makana.mechwar.domain.battle.phases.groundmovement;

import io.makana.mechwar.domain.battle.BattleContext;
import io.makana.mechwar.domain.battle.phases.groundmovement.validators.MoveOrderRequestValidator;
import io.makana.mechwar.domain.players.Player;
import io.makana.mechwar.domain.players.PlayerClient;
import io.makana.mechwar.domain.players.PlayerMovementRequest;
import io.makana.mechwar.domain.players.PlayerMovementResponse;
import io.makana.mechwar.domain.support.calculators.UnitRatioPerPlayerCalculator;
import io.makana.mechwar.domain.units.Unit;
import io.makana.mechwar.domain.units.UnitState;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class GroundMovementPhase {

    @Autowired
    private final UnitRatioPerPlayerCalculator unitRatioPerPlayerCalculator;

    public GroundMovementPhaseResult moveUnits(@NonNull final BattleContext startingBattleContext) {
        log.info("Beginning ground movement phase");
        final Map<Player, List<UnitState>> unitStatesByPlayer = startingBattleContext.getUnitsByPlayer();
        final Map<Player, List<Unit>> unitsToMoveByPlayer = unitStatesByPlayer.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream().map(UnitState::getUnit).collect(Collectors.toList())
                ));
        Map<Player, Integer> playerUnitCountRatios = calculatePlayerUnitCountRatios(unitsToMoveByPlayer);
        boolean isMovementComplete = unitsToMoveByPlayer.isEmpty();
        BattleContext currentContext = startingBattleContext;
        while (!isMovementComplete) {
            log.debug("Units to move: {}", unitsToMoveByPlayer);
            for (Player player : currentContext.getPlayersInOrderOfInitiative()) {
                log.debug("On {}", player);
                if (unitsToMoveByPlayer.containsKey(player)) {
                    final PlayerClient playerClient = currentContext.getPlayerClient(player);
                    final List<Unit> unitsAllowedToMove = unitsToMoveByPlayer.get(player);
                    final PlayerMovementRequest playerMovementRequest = new PlayerMovementRequest(
                            player,
                            unitsAllowedToMove,
                            playerUnitCountRatios.get(player)
                    );
                    log.debug("Sending {} to {}", playerMovementRequest, player);
                    final PlayerMovementResponse playerMovementResponse = playerClient.getMovementOrder(playerMovementRequest);
                    log.debug("Received {} movement response from {}", playerMovementResponse, player);
                    if (!playerMovementResponse.getPlayer().equals(player)) {
                        throw new IllegalArgumentException("Wrong player! Expected " +
                                player +
                                " but received " +
                                playerMovementResponse.getPlayer());
                    }
                    final List<MoveOrderRequest> unitMoveOrders = playerMovementResponse.getMoveOrderRequests();
                    // validate movement orders
                    validateMoveOrders(currentContext, playerMovementRequest, unitMoveOrders);
                    /** @TODO apply movement orders **/
                    updateAvailableUnitsToMove(unitsToMoveByPlayer, player, unitsAllowedToMove, unitMoveOrders);
                    /** @TODO update ratio **/
                    if (!unitsToMoveByPlayer.isEmpty()) {
                        playerUnitCountRatios = calculatePlayerUnitCountRatios(unitsToMoveByPlayer);
                    }
                } else {
                    log.debug("Player [{}] had no more available units to move, skipping", player);
                }
            }
            log.debug("Units to move per player size: {}", unitsToMoveByPlayer.size());
            isMovementComplete = unitsToMoveByPlayer.isEmpty();
            if (isMovementComplete) {
                log.info("All available units have moved.");
            }
        }
        /** @TODO return results of ground movement **/
        return new GroundMovementPhaseResult(startingBattleContext, currentContext);
    }

    private void validateMoveOrders(@NonNull final BattleContext battleContext,
                                    @NonNull final PlayerMovementRequest playerMovementRequest,
                                    @NonNull final List<MoveOrderRequest> unitMoveOrders) throws InvalidMoveOrder {
        if (unitMoveOrders.isEmpty()) {
            throw new InvalidMoveOrder("Empty move order not allowed!");
        }
        if (unitMoveOrders.size() != playerMovementRequest.getRequiredToMoveCount()) {
            throw new InvalidMoveOrder("Incorrect number of units moved - should be " +
                    playerMovementRequest.getRequiredToMoveCount());
        }
        for (final MoveOrderRequest moveOrderRequest : unitMoveOrders) {
            MoveOrderRequestValidator.validate(battleContext, playerMovementRequest, moveOrderRequest);
        }
    }

    /**
     * Recalculate the available units left to move for each player
     *
     * @param unitsToMovePerPlayer players and their available to move units
     * @param player               player who moved
     * @param unitsAllowedToMove   the units that were available to move
     * @param unitMoveOrders       the move orders
     */
    private void updateAvailableUnitsToMove(final Map<Player, List<Unit>> unitsToMovePerPlayer,
                                            final Player player,
                                            final List<Unit> unitsAllowedToMove,
                                            final List<MoveOrderRequest> unitMoveOrders) {
        final List<Unit> unitsMoved = unitMoveOrders.stream()
                .map(MoveOrderRequest::getUnitToMove)
                .collect(Collectors.toList());
        log.debug("These units moved and cannot move again this turn: {}", unitsMoved);
        final List<Unit> newUnitList = new ArrayList<>();
        newUnitList.addAll(unitsAllowedToMove);
        newUnitList.removeAll(unitsMoved);
        if (newUnitList.isEmpty()) {
            unitsToMovePerPlayer.remove(player);
        } else {
            unitsToMovePerPlayer.put(player, newUnitList);
        }
    }

    private Map<Player, Integer> calculatePlayerUnitCountRatios(Map<Player, List<Unit>> availableUnitsToMove) {
        Map<Player, Integer> ratios = unitRatioPerPlayerCalculator.calculateUnitRatios(availableUnitsToMove);
        log.debug("New player/unit ratios calculated: {}", ratios);
        return ratios;
    }
}
