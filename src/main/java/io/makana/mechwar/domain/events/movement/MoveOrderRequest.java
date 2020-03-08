package io.makana.mechwar.domain.events.movement;

import io.makana.mechwar.domain.players.Player;
import io.makana.mechwar.domain.units.Unit;
import io.makana.mechwar.domain.units.UnitId;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
public class MoveOrderRequest {
    private Player player;
    private Unit unitToMove;
    @Singular("addToMovePath")
    private List<MovePath> movePath;
}
