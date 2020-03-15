package io.makana.mechwar.domain.board;

import io.makana.mechwar.domain.board.terrain.Terrain;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.Map;

/**
 * Models a single, six-sided space on a game board.
 */
@Value
public class Hex {
    private final int numberLabel;
    private final int elevation;
    @NonNull private final Terrain terrain;
    @NonNull private final Map<HexFacing, Hex> neighborsByDirection;

}
