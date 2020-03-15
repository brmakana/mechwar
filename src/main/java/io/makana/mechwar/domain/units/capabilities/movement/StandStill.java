package io.makana.mechwar.domain.units.capabilities.movement;

import io.makana.mechwar.domain.board.terrain.Terrain;

public class StandStill implements MovementMode {
    private static final StandStill instance = new StandStill();

    private StandStill() {}

    public static final StandStill getInstance() {
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
