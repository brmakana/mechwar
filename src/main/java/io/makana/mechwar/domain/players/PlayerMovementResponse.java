package io.makana.mechwar.domain.players;

import io.makana.mechwar.domain.battle.phases.groundmovement.MoveOrderRequest;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
public class PlayerMovementResponse {
    @NonNull
    private Player player;
    @NonNull
    private final List<MoveOrderRequest> moveOrderRequests;
}
