package org.learning.webhook;

import org.learning.webhook.retry.CappedPolicy;
import org.learning.webhook.retry.ExponentialBackoff;
import org.learning.webhook.retry.FixedDelay;
import org.learning.webhook.retry.RetryPolicy;

public class RetryDemo {

    static void printSchedule(RetryPolicy policy, int maxAttempts) {

        if (maxAttempts < 1) {
            throw new IllegalArgumentException("maxAttempts must be >= 1");
        }

        if (policy == null) {
            throw new IllegalArgumentException("policy must not be null");
        }

        System.out.println(policy.getClass().getSimpleName() + " (maxAttempts = " + maxAttempts + ")");

        long totalWaited = 0;

        for (int attempt = 1; policy.shouldRetry(attempt, maxAttempts); attempt++) {
            long wait = policy.delaySeconds(attempt);
            totalWaited += wait;
            System.out.println("  After attempt " + attempt + " fails: wait " + wait + "s (total " + totalWaited + "s)");
        }

        System.out.println();
    }

    public static void main(String[] args){
        printSchedule(new ExponentialBackoff(60), 10);
        printSchedule(new FixedDelay(5), 4);
        printSchedule(new CappedPolicy(new FixedDelay(90), 30), 3);
        printSchedule(new CappedPolicy(new ExponentialBackoff(60), 10), 6);

        System.out.println("--- edge cases ---");
        System.out.println("Exponential, attempt 100: " + new ExponentialBackoff(60).delaySeconds(100));

        try {
            new ExponentialBackoff(60).delaySeconds(0);
            System.out.println("BUG: attempt 0 was accepted");
        } catch (IllegalArgumentException e) {
            System.out.println("Rejected: " + e.getMessage());
        }

        try {
            new ExponentialBackoff(0);
            System.out.println("BUG: cap 0 was accepted");
        } catch (IllegalArgumentException e) {
            System.out.println("Rejected: " + e.getMessage());
        }

        try {
            new FixedDelay(-1);
            System.out.println("BUG: negative delay was accepted");
        } catch (IllegalArgumentException e) {
            System.out.println("Rejected: " + e.getMessage());
        }

        try {
            new CappedPolicy(null, 30);
            System.out.println("BUG: null inner policy was accepted");
        } catch (IllegalArgumentException e) {
            System.out.println("Rejected: " + e.getMessage());
        }
    }
}
