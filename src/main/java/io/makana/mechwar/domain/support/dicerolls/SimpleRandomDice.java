package io.makana.mechwar.domain.support.dicerolls;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class SimpleRandomDice implements Dice {

    private final Random random = new Random();

    @Override
    public int roll2D6() {
        return random.nextInt(12)+1;
    }

    @Override
    public int roll1D6() {
        return random.nextInt(6)+1;
    }
}
