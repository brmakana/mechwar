package io.makana.mechwar.domain.entities.board.terrain;

public class Clear extends Terrain {

    public Clear() {
        super("Clear");
    }

    @Override
    public int getObstructionLevelModifier() {
        return 0;
    }

    @Override
    public int getHeatModifier() {
        return 0;
    }

    @Override
    public int movementCost() {
        return 1;
    }

}
