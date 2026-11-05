package org.learning.webhook.schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;

public final class RetryScheduler {

    private final PriorityQueue<ScheduledDelivery> deliveryPriorityQueue =
            new PriorityQueue<>(new ScheduledDeliveryComparator());

    public void schedule(ScheduledDelivery delivery){
        Objects.requireNonNull(delivery, "delivery");
        deliveryPriorityQueue.offer(delivery);
    }

    public List<ScheduledDelivery> pollDue(long nowEpochSeconds){
        List<ScheduledDelivery> dueDeliveries = new ArrayList<>();

        while (!deliveryPriorityQueue.isEmpty()){
            ScheduledDelivery nextDelivery = deliveryPriorityQueue.peek();

            if (nextDelivery.dueAtEpochSeconds() > nowEpochSeconds) {
                break;
            }

            dueDeliveries.add(deliveryPriorityQueue.poll());
        }

        return dueDeliveries;
    }

    public int size() {
        return deliveryPriorityQueue.size();
    }
}
