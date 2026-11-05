package org.learning.webhook.schedule;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class RetryScheduler {

    private final PriorityQueue<ScheduledDelivery> deliveryPriorityQueue =
            new PriorityQueue<>(ScheduledDelivery.DUE_ORDER);

    public void schedule(ScheduledDelivery delivery){
        Objects.requireNonNull(delivery, "delivery");
        deliveryPriorityQueue.offer(delivery);
    }

    public Optional<ScheduledDelivery> peekNext(){
        return Optional.ofNullable(deliveryPriorityQueue.peek());
    }

    public Map<String, Long> countByEndpoint(){
        return deliveryPriorityQueue.stream().collect(Collectors.groupingBy(ScheduledDelivery::endpointId, Collectors.counting())); // because we are doing counting not ordering
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
