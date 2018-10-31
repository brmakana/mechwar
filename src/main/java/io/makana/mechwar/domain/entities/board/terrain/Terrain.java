package io.makana.mechwar.domain.entities.board.terrain;

import java.util.Optional;

public abstract class Terrain {

    private String name;
    public Terrain(String name) {
        this.name = name;
    }

    public abstract int getObstructionLevelModifier();
    public abstract int getHeatModifier();
    public abstract int movementCost();

    public Optional<Integer> constructionFactor() {
        return Optional.empty();
    }

    public boolean deadlyAtmosphere() {
        return false;
    }
    public Optional<Integer> dealsDamage() {
        return Optional.empty();
    }

    public String getName() {
        return name;
    }

}
