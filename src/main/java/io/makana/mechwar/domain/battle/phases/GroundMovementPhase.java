package io.makana.mechwar.domain.battle.phases;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class GroundMovementPhase {

//    @Autowired
//    private GameUnitRepository gameUnitRepository;
//
//    @Autowired
//    private UnitRatioPerPlayerCalculator unitRatioPerPlayerCalculator;


    public void moveUnits( int round) {
//        log.info("Beginning ground movement phase for game {}, round {}", gameId, round);
//        // get ground units for players
//        Players players = playerRepository.getPlayers(gameId);
//        Map<Player, List<GameUnitId>> unitsByPlayer = getUnitsByPlayer(gameId, players);
//        // calculate unit ratio
//        Map<Player, Integer> unitRatioByPlayer = getUnitRatioByPlayer(unitsByPlayer);
//        boolean moveOrdersComplete = false;
//        // repeat while still unmoved units
//        while (!moveOrdersComplete) {
//            //  ask player for move order
//            for (Player player : players.getPlayers()) {
//                log.info("Asking {} for move request", player);
//                PlayerClient client = playerRepository.getClientForPlayer(player);
//                MoveOrderRequest moveOrderRequest = client.requestGroundMovement(
//                        gameId,
//                        round,
//                        new HashSet<>(),
//                        unitRatioByPlayer.get(player));
//                log.info("Player's move request: {}", moveOrderRequest);
//                // validate request
//
//            }
//            moveOrdersComplete = true;
//        }
//        log.info("Ground movement phase complete for game {}, round {}", gameId, round);
    }

//    private Map<Player, List<GameUnitId>> getUnitsByPlayer(GameId gameId, Players players) {
//        Map<Player, List<GameUnitId>> unitsByPlayer = players.getPlayers().stream().collect(Collectors.toMap(
//                p -> p,
//                p -> gameUnitRepository.getUnitIdsForPlayer(gameId, p)
//        ));
//        return unitsByPlayer;
//    }
//
//    private Map<Player, Integer> getUnitRatioByPlayer(Map<Player, List<GameUnitId>> unitsByPlayer) {
//        Map<Player, Integer> unitRatioByPlayer = unitRatioPerPlayerCalculator.calculateUnitRatios(unitsByPlayer);
//        return unitRatioByPlayer;
//    }

}
