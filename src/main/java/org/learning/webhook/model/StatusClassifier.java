package org.learning.webhook.model;

public final class StatusClassifier {
    public static DeliveryOutcome decide(int status) {
        if (status < 100 || status > 599) {
            throw new IllegalArgumentException("Invalid HTTP status: " + status);
        }
        return switch (status / 100) {               // 404 / 100 = 4: integer division as a tool!
            case 2 -> DeliveryOutcome.SUCCESS;                     // 200–299: delivered
            case 4 -> (status == 429 || status == 408) ? DeliveryOutcome.RETRY      // 429 = "Too Many Requests": retry later
                    : DeliveryOutcome.GIVE_UP;   // other 4xx: the request itself is bad
            case 5 -> DeliveryOutcome.RETRY;                       // 5xx: the receiver's server failed
            default -> DeliveryOutcome.GIVE_UP;
        };
    }

    private StatusClassifier() { }   // no instances: this is a static utility class
}
