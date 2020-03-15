package io.makana.mechwar.domain.battle.phases.groundmovement;

public class InvalidMoveOrder extends RuntimeException {

    public InvalidMoveOrder() {
        super();
    }

    public InvalidMoveOrder(String message) {
        super(message);
    }

    public InvalidMoveOrder(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidMoveOrder(Throwable cause) {
        super(cause);
    }

    protected InvalidMoveOrder(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
