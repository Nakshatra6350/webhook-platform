package org.learning.webhook.error;

public class WebhookException extends RuntimeException {
    public WebhookException(String message) {
        super(message);
    }
    public WebhookException(String message, Throwable cause){
        super(message,cause);
    }
}
