package io.makana.mechwar.domain.entities.board;

/**
 * Models the direction a <code>{@link GameBoard}</code>'s "up" is oriented towards. If a <code>{@link GameBoard}</code>
 * were facing "naturally", ie with the top row actually on the top, the <code>{@link GameBoardDirection}</code> would be <code>NORTH</code>.
 */
public enum GameBoardDirection {
    NORTH,
    SOUTH,
    EAST,
    WEST
}
