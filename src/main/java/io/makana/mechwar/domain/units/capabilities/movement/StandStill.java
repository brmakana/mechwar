package io.makana.mechwar.domain.units.capabilities.movement;

import io.makana.mechwar.domain.entities.board.terrain.Terrain;

public class StandStill implements MovementCapability {
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
