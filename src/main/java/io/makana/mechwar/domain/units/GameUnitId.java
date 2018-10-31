package io.makana.mechwar.domain.units;

import lombok.Value;

import java.util.UUID;

@Value
public class GameUnitId {

    private UUID uuid;

    public GameUnitId() {
        uuid = UUID.randomUUID();
    }

}
