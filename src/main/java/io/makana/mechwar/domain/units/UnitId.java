package io.makana.mechwar.domain.units;

import lombok.Value;

import java.util.UUID;

@Value
public class UnitId {

    private final UUID uuid;

    public UnitId() {
        uuid = UUID.randomUUID();
    }

}
