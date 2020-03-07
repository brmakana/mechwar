package io.makana.mechwar.domain.players;

public interface PlayerClient {
    PlayerMovementResponse getMovementOrder(PlayerMovementRequest playerMovementRequest);
}
