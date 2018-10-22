package io.makana.mechwar.domain.game;

import lombok.Value;

import java.util.UUID;

@Value
public class GameId {
    private UUID id;

    public GameId() {
        id = UUID.randomUUID();
    }
}
