package io.makana.mechwar.domain.entities.board;

import io.makana.mechwar.domain.entities.board.terrain.Terrain;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

/**
 * Models a single, six-sided space on a game board. All hexes have an id, a terrain type, and an elevation.
 * The identifier is a four digit number, comprised of the column and row number together. Column values are 01 through 15,
 * and row values are 01 through 17. The first hex is 0101, and the last 1517.
 */
@Value
@Builder
public class Hex {

    private int id;
    private int elevation;

    @Singular("terrain")
    private List<Terrain> terrain;
}
