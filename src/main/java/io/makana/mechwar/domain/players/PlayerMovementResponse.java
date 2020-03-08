package io.makana.mechwar.domain.players;

import io.makana.mechwar.domain.events.movement.MoveOrderRequest;
import io.makana.mechwar.domain.units.Unit;
import lombok.Value;

import java.util.List;
import java.util.Map;

@Value
public class PlayerMovementResponse {

    private final List<MoveOrderRequest> moveOrderRequests;

}
