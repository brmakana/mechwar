package io.makana.mechwar.domain.entities.board;

import io.makana.mechwar.domain.FourPointCompass;
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
    /**
     * Models the direction a <code>{@link GameBoard}</code>'s "up" is oriented towards. If a <code>{@link GameBoard}</code>
     * were facing "naturally", ie with the top row actually on the top, the <code>{@link FourPointCompass}</code> would be <code>NORTH</code>.
     */
    private FourPointCompass facing;
    @Singular
    private Map<FourPointCompass, GameBoard> neighbors;
}
