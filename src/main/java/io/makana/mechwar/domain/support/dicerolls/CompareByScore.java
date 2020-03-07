package io.makana.mechwar.domain.support.dicerolls;

import java.util.Comparator;

public class CompareByScore implements Comparator<RollResult> {

    @Override
    public int compare(RollResult o1, RollResult o2) {
        return Integer.compare(o1.getRoll(), o2.getRoll());
    }
}
