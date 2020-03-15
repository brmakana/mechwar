package io.makana.mechwar.domain.units.capabilities.movement;

import io.makana.mechwar.domain.board.terrain.Terrain;

public class Walk implements MovementMode {

    private static final Walk instance = new Walk();

    private Walk() {}

    public static Walk getInstance() {
        return instance;
    }

    @Override
    public int getHeatModifier() {
        return 0;
    }

    @Override
    public int getToHitModifier() {
        return 0;
    }

    @Override
    public int getToBeHitModifier() {
        return 0;
    }

    @Override
    public boolean requirePilotingSkillRoll(Terrain terrain) {
        return false;
    }
}
