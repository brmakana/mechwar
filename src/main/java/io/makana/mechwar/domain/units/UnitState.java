package io.makana.mechwar.domain.units;

import io.makana.mechwar.domain.board.Hex;
import io.makana.mechwar.domain.board.HexFacing;
import io.makana.mechwar.domain.players.Player;
import lombok.NonNull;
import lombok.Value;

@Value
public class UnitState {

    @NonNull private final Player owningPlayer;
    @NonNull private final Unit unit;
    @NonNull private final HexFacing facing;
    @NonNull private final Hex location;
    /** @TODO movement points remaining **/
    /** @TODO heat **/
    /** @TODO damage **/
    /** @TODO pilot/crew **/

}
