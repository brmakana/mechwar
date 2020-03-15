package io.makana.mechwar.domain.units.capabilities.movement;

import io.makana.mechwar.domain.board.terrain.Terrain;

public interface MovementMode {
    int getHeatModifier();
    int getToHitModifier();
    int getToBeHitModifier();
    boolean requirePilotingSkillRoll(Terrain terrain);
}
