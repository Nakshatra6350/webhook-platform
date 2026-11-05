package org.learning.webhook.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StatusClassifierTest {
//    @Test
//    void classifies503AsRetry(){
//        assertEquals(DeliveryOutcome.RETRY, StatusClassifier.decide(503));
//    }
//
//    @Test
//    void classifies404AsGiveUp(){
//        assertEquals(DeliveryOutcome.GIVE_UP, StatusClassifier.decide(404));
//    }

    @ParameterizedTest
    @CsvSource({
            "200, SUCCESS",
            "301, GIVE_UP",
            "404, GIVE_UP",
            "408, RETRY",
            "429, RETRY",
            "500, RETRY",
            "503, RETRY"
    })
    void classifiesStatus(int status, DeliveryOutcome expected) {
        assertEquals(expected, StatusClassifier.decide(status));
    }

    @ParameterizedTest
    @ValueSource(ints = {99, 600, -1})
    void shouldThrowExceptionForInvalidStatusCodes(int status) {

        assertThrows(
                IllegalArgumentException.class,
                () -> StatusClassifier.decide(status)
        );
    }
}
