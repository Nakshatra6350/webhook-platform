package org.learning.webhook.error;

import org.learning.webhook.model.DeliveryOutcome;
import org.learning.webhook.model.StatusClassifier;

public class DeliveryException extends WebhookException {
    private final int httpStatus;
    public DeliveryException(String message, int httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public DeliveryException(String message, int httpStatus, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public int getHttpStatus(){
        return httpStatus;
    }

    public boolean isRetryable(){
        if(httpStatus == -1){
            return true; // -1 means no response: the receiver may or may not have processed it. Retrying is safe only because receivers deduplicate on event id.
        }

        return StatusClassifier.decide(httpStatus) == DeliveryOutcome.RETRY;
    }
}
