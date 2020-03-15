package io.makana.mechwar.domain.board;

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
    private CompassDirection facing;
    @Singular
    private Map<CompassDirection, GameBoard> neighbors;
}
