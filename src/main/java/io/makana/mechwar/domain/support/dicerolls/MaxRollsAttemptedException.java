package io.makana.mechwar.domain.support.dicerolls;

public class MaxRollsAttemptedException extends Exception {
    public MaxRollsAttemptedException() {
        super();
    }

    public MaxRollsAttemptedException(String message) {
        super(message);
    }

    public MaxRollsAttemptedException(String message, Throwable cause) {
        super(message, cause);
    }

    public MaxRollsAttemptedException(Throwable cause) {
        super(cause);
    }

    protected MaxRollsAttemptedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
