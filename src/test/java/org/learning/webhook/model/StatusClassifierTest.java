package org.learning.webhook.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatusClassifierTest {
    @Test
    void classifies503AsRetry(){
        assertEquals(DeliveryOutcome.RETRY, StatusClassifier.decide(503));
    }

    @Test
    void classifies404AsGiveUp(){
        assertEquals(DeliveryOutcome.GIVE_UP, StatusClassifier.decide(404));
    }
}
