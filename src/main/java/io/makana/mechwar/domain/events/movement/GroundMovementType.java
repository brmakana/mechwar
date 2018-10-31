package io.makana.mechwar.domain.events.movement;

public abstract class GroundMovementType {

    public abstract int getHeatModifier();
    public abstract int getToHitModifier();
    public abstract int getToBeHitModifier();
    public abstract boolean requirePilotingSkillRoll();
}
