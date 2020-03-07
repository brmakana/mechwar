package io.makana.mechwar.domain.events.movement;

import io.makana.mechwar.domain.players.Player;
import io.makana.mechwar.domain.units.GameUnitId;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class MoveOrderRequest {
    private Player player;
    private int round;
    private GameUnitId unitToMove;
    @Singular("addMovePath")
    private List<MovePath> movePath;
}
