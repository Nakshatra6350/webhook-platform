package org.learning.webhook.retry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CappedPolicyTest {
    @Test
    void capsDelayAboveMaximum() {
        assertEquals(30, new CappedPolicy(new FixedDelay(90), 30).delaySeconds(1));
    }
}
