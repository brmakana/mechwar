package io.makana.mechwar.domain.battle;

import io.makana.mechwar.domain.battle.phases.InitiativePhase;
import io.makana.mechwar.domain.battle.phases.InitiativePhaseResult;
import io.makana.mechwar.domain.events.movement.MoveOrderRequest;
import io.makana.mechwar.domain.players.Player;
import io.makana.mechwar.domain.players.PlayerClient;
import io.makana.mechwar.domain.players.PlayerMovementRequest;
import io.makana.mechwar.domain.players.PlayerMovementResponse;
import io.makana.mechwar.domain.support.dicerolls.MaxRollsAttemptedException;
import io.makana.mechwar.domain.support.dicerolls.RollResult;
import io.makana.mechwar.domain.units.Unit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class Battle {

    private final InitiativePhase initiativePhase;
    private BattleContext battleContext;

    public BattleResult fight(final BattleInput battleInput) throws MaxRollsAttemptedException {
        int turn = 0;
        boolean isFinished = false;
        while (!isFinished) { // also check for turn limit?
            turn++;
            log.info("Starting turn {}", turn);
            // roll initiative
            final List<Player> players = battleInput.getPlayers();
            final InitiativePhaseResult initiativePhaseResult = initiativePhase.rollInitiative(players);
            log.debug("Initiative roll result for turn {}: {}", turn, initiativePhaseResult);
            final List<RollResult> initiativeRollResults = initiativePhaseResult.getRollResults();
            final List<Player> playersInOrderOfInitiative = initiativeRollResults
                    .stream()
                    .map(RollResult::getPlayer)
                    .collect(Collectors.toList());
            // ground movement
            Map<Player, List<Unit>> unitsToMovePerPlayer = getUnitsPerPlayer(battleInput, playersInOrderOfInitiative);
            Map<Player, Integer> playerUnitCountRatios = calculatePlayerUnitCountRatios(unitsToMovePerPlayer);
            while (!unitsToMovePerPlayer.isEmpty()) {
                for (Player player : playersInOrderOfInitiative) {
                    if (unitsToMovePerPlayer.containsKey(player)) {
                        final PlayerClient playerClient = battleInput.getPlayerClient(player);
                        final PlayerMovementRequest playerMovementRequest = new PlayerMovementRequest(
                                unitsToMovePerPlayer.get(player),
                                playerUnitCountRatios.get(player)
                        );
                        final PlayerMovementResponse playerMovementResponse = playerClient.getMovementOrder(playerMovementRequest);
                        final Map<Unit, MoveOrderRequest> unitMoveOrders = playerMovementResponse.getUnitMoveOrders();
                        /** @TODO validate movement orders **/
                        /** @TODO apply movement orders **/
                        unitMoveOrders.keySet().stream().forEach(unit -> {
                            unitsToMovePerPlayer.get(player).remove(unit);
                        });
                        if (unitsToMovePerPlayer.get(player).isEmpty()) {
                            unitsToMovePerPlayer.remove(player);
                        }
                        /** @TODO update ratio **/
                        playerUnitCountRatios = calculatePlayerUnitCountRatios(unitsToMovePerPlayer);
                    } else {
                        log.debug("Player [{}] had no more available units to move, skipping", player);
                    }
                }
            }
            // aerospace movement
            // weapon attack phase
            // physical attack phase
            // heat phase
            // end phase

            TurnContext turnContext = new TurnContext();


        }
        return null;
    }

    private Map<Player, List<Unit>> getUnitsPerPlayer(BattleInput battleInput, List<Player> playersInOrderOfInitiative) {
        Map<Player, List<Unit>> unitsToMovePerPlayer = new HashMap<>();
        playersInOrderOfInitiative
                .forEach(player -> {
                    final List<Unit> playersUnits = battleInput.getUnits(player);
                    unitsToMovePerPlayer.put(player, playersUnits);
                });
        return unitsToMovePerPlayer;
    }

    private Map<Player, Integer> calculatePlayerUnitCountRatios(Map<Player, List<Unit>> unitsToMovePerPlayer) {
        final Map<Player, Integer> playerUnitCountRatios = unitsToMovePerPlayer
                .keySet()
                .stream()
                .collect(Collectors.toMap(Function.identity(), player -> unitsToMovePerPlayer.get(player).size()));
        return playerUnitCountRatios;
    }
}
