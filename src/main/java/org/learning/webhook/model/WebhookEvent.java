package org.learning.webhook.model;

public record WebhookEvent(String id, String eventType, String payload, long createdAtEpochSeconds) {
    public WebhookEvent{
        if(id == null || id.isBlank()){
            throw new IllegalArgumentException("id is required");
        }

        if(eventType == null || eventType.isBlank()){
            throw new IllegalArgumentException("event type is required");
        }

        if(payload == null){
            throw new IllegalArgumentException("payload is required");
        }

        if (createdAtEpochSeconds <= 0) {
            throw new IllegalArgumentException("createdAtEpochSeconds must be > 0, got: " + createdAtEpochSeconds);
        }
    }
}
