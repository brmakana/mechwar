package io.makana.mechwar.domain.support.calculators;

import io.makana.mechwar.domain.players.Player;
import io.makana.mechwar.domain.entities.Team;
import io.makana.mechwar.domain.units.GameUnitId;
import lombok.Value;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

import static org.junit.Assert.*;
@RunWith(Parameterized.class)
public class UnitRatioPerPlayerCalculatorTest {

    @Value
    public static class TestParams {
        public int player1Count;
        public int player2Count;
        public int expectedPlayer1Count;
        public int expectedPlayer2Count;
    }

    private TestParams testParams;

    public UnitRatioPerPlayerCalculatorTest(TestParams testParams) {
        this.testParams = testParams;
    }

    @Parameterized.Parameters
    public static Collection<TestParams> data() {
        return Arrays.asList(
            new TestParams(1, 1, 1, 1),
            new TestParams(1, 4, 1, 4),
            new TestParams(2, 4, 1, 2),
            new TestParams(3, 10, 1, 4),
            new TestParams(3, 3, 1, 1)
        );
    }

    @Test
    public void calculates_unit_ratios() {
        UnitRatioPerPlayerCalculator cut = new UnitRatioPerPlayerCalculator();
        Map<Player, List<GameUnitId>> input = new HashMap<>();
        Player player1 = new Player("player 1", new Team("team 1"));
        input.put(player1, getGameUnitList(testParams.player1Count));
        Player player2 = new Player("player 2", new Team("team 2"));
        input.put(player2, getGameUnitList(testParams.player2Count));

        Map<Player, Integer> expected = new HashMap<>();
        expected.put(player1, testParams.expectedPlayer1Count);
        expected.put(player2, testParams.expectedPlayer2Count);

        Map<Player, Integer> actual = cut.calculateUnitRatios(input);
        assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void disallows_empty_argument() {
        UnitRatioPerPlayerCalculator cut = new UnitRatioPerPlayerCalculator();
        Map<Player, List<GameUnitId>> input = new HashMap<>();

        cut.calculateUnitRatios(input);
    }

    @Test(expected = IllegalArgumentException.class)
    public void players_must_have_at_least_one_unit() {
        UnitRatioPerPlayerCalculator cut = new UnitRatioPerPlayerCalculator();
        Map<Player, List<GameUnitId>> input = new HashMap<>();
        Player player1 = new Player("player 1", new Team("team 1"));
        input.put(player1, getGameUnitList(0));
        Player player2 = new Player("player 2", new Team("team 2"));
        input.put(player2, getGameUnitList(0));
        cut.calculateUnitRatios(input);
    }

    private List<GameUnitId> getGameUnitList(int count) {
        List<GameUnitId> units = new ArrayList<>();
        for (int x = 0; x < count; x++) {
            units.add(new GameUnitId());
        }
        return units;
    }
}