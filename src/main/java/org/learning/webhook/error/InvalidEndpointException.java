package org.learning.webhook.error;

public class InvalidEndpointException extends WebhookException {
    public InvalidEndpointException(String message) {
        super(message);
    }
}
