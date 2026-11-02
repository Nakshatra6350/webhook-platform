package org.learning.webhook.retry;

public final class FixedDelay implements RetryPolicy {
    private final long seconds;

    public FixedDelay(long seconds) {
        if(seconds <= 0){
            throw new IllegalArgumentException("Seconds must be greater than 0, got : " + seconds);
        }
        this.seconds = seconds;
    }

    @Override
    public long delaySeconds(int attempt) {
        if (attempt < 1) {
            throw new IllegalArgumentException("attempt must be >= 1, got: " + attempt);
        }
        return seconds;
    }
}
