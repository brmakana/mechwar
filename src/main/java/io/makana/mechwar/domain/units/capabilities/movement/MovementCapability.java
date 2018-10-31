package io.makana.mechwar.domain.units.capabilities.movement;

import io.makana.mechwar.domain.entities.board.terrain.Terrain;

public interface MovementCapability {
    int getHeatModifier();
    int getToHitModifier();
    int getToBeHitModifier();
    boolean requirePilotingSkillRoll(Terrain terrain);
}
