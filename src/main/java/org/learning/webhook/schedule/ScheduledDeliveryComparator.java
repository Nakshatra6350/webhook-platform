package org.learning.webhook.schedule;

import java.util.Comparator;

public final class ScheduledDeliveryComparator implements Comparator<ScheduledDelivery> {

    @Override
    public int compare(ScheduledDelivery d1, ScheduledDelivery d2){
        int dueTimeComparison = Long.compare(d1.dueAtEpochSeconds(), d2.dueAtEpochSeconds());
        if(dueTimeComparison != 0){
            return dueTimeComparison;
        }

        int eventIdComparison = d1.eventId().compareTo(d2.eventId());

        if (eventIdComparison != 0) {
            return eventIdComparison;
        }

        int byAttempt = Integer.compare(d1.attempt(), d2.attempt());
        if (byAttempt != 0) {
            return byAttempt;
        }

        return d1.endpointId().compareTo(d2.endpointId());
    }
}
