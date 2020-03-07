package io.makana.mechwar.domain.entities;

import lombok.Value;

import java.util.UUID;

@Value
public class EntityId {
    private UUID uuid;

    public EntityId() {
        this.uuid = UUID.randomUUID();
    }
}
