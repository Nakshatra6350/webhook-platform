package org.learning.webhook.retry;

public final class CappedPolicy implements RetryPolicy{
    private final RetryPolicy inner;
    private final long maxSeconds;


    public CappedPolicy(RetryPolicy inner, long maxSeconds) {
        if(inner == null){
            throw new IllegalArgumentException("Inner must not be null");
        }

        if(maxSeconds < 0){
            throw new IllegalArgumentException("Max seconds must be greater than or equal to 0, got : " + maxSeconds);
        }

        this.inner = inner;
        this.maxSeconds = maxSeconds;
    }

    @Override
    public long delaySeconds(int attempt) {
        return Math.min(inner.delaySeconds(attempt), maxSeconds);   // the ONE thing we change
    }

    @Override
    public boolean shouldRetry(int attempt, int maxAttempts) {
        return inner.shouldRetry(attempt, maxAttempts);             // pass it through unchanged
    }


}
