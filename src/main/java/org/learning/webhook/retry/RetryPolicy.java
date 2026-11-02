package org.learning.webhook.retry;

//Attempts are numbered from 1. Attempt 1 is the first delivery, which happens immediately.
//delaySeconds(attempt) = how long to wait after attempt attempt fails, before trying again.
//shouldRetry(attempt, maxAttempts) = after attempt attempt fails, is another try allowed?

public interface RetryPolicy {
    long delaySeconds(int attempt);
    default boolean shouldRetry(int attempt, int maxAttempts){
        if (attempt < 1) {
            throw new IllegalArgumentException("attempt must be >= 1");
        }

        if (maxAttempts < 1) {
            throw new IllegalArgumentException("maxAttempts must be >= 1");
        }
        return attempt < maxAttempts;
    }
}
