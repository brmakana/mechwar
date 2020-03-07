package io.makana.mechwar.domain.entities;

public interface EntityRepository {

    Entity getById(EntityId entityId);
}
