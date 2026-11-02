package org.learning.webhook.retry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExponentialBackoffTest {
    @Test
    void capsVeryLargeAttempts() {
        assertEquals(60, new ExponentialBackoff(60).delaySeconds(100));
    }

    @Test
    void rejectsAttemptZero() {
        assertThrows(IllegalArgumentException.class,
                () -> new ExponentialBackoff(60).delaySeconds(0));
    }
}
