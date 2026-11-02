package org.learning.webhook.retry;

public final class ExponentialBackoff implements RetryPolicy{
    private final long capSeconds;

    public ExponentialBackoff(long capSeconds) {
        if(capSeconds <= 0){
            throw new IllegalArgumentException("Cap must be greater than 0, got : " + capSeconds);
        }
        this.capSeconds = capSeconds;
    }

    @Override
    public long delaySeconds(int attempt) {
        if (attempt < 1) {
            throw new IllegalArgumentException("attempt must be >= 1, got: " + attempt);
        }

        if (attempt - 1 > 62) {                // the shift would overflow, so the true delay is beyond any cap
            return capSeconds;
        }

        long delay = 1L << (attempt - 1);
        return Math.min(delay, capSeconds);
    }
}
