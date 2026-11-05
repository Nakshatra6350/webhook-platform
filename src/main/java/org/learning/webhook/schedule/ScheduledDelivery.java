package org.learning.webhook.schedule;

public record ScheduledDelivery(String eventId, String endpointId, int attempt, long dueAtEpochSeconds) {
    public ScheduledDelivery{
        if(eventId == null || eventId.isBlank()){
            throw new IllegalArgumentException("Event id is required");
        }

        if(endpointId == null || endpointId.isBlank()){
            throw new IllegalArgumentException("Endpoint id is required");
        }

        if(attempt <= 0){
            throw new IllegalArgumentException("Attempt must be greater than or equal to 1, got: " + attempt);
        }

        if(dueAtEpochSeconds  <= 0){
            throw new IllegalArgumentException("dueAtEpochSeconds must be greater than 0, got: " + dueAtEpochSeconds);
        }
    }
}
