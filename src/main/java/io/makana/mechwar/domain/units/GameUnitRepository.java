package io.makana.mechwar.domain.units;

public interface GameUnitRepository {



    Unit getGameUnitById(GameUnitId unitToMove);
}
