package io.makana.mechwar.domain.entities.board;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.Map;


/**
 * Models a game board.
 */
@Value
@Builder
public class GameBoard {
    private Map<Integer, Hex> hexesById;
    private GameBoardDirection facing;
    @Singular
    private Map<GameBoardDirection, GameBoard> neighbors;
}
