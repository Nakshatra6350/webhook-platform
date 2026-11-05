package org.learning.webhook.schedule;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RetrySchedulerTest {
    @Test
    void shouldPollDeliveriesInDueTimeOrder(){
        RetryScheduler retryScheduler = new RetryScheduler();
        ScheduledDelivery d1 =
                new ScheduledDelivery("event-C", "endpoint-1", 1, 500);

        ScheduledDelivery d2 =
                new ScheduledDelivery("event-A", "endpoint-1", 1, 200);

        ScheduledDelivery d3 =
                new ScheduledDelivery("event-B", "endpoint-1", 1, 400);

        retryScheduler.schedule(d1);
        retryScheduler.schedule(d2);
        retryScheduler.schedule(d3);

        List<ScheduledDelivery> dueDeliveries = retryScheduler.pollDue(500);

        assertEquals(List.of(d2,d3,d1), dueDeliveries);

        assertEquals(0, retryScheduler.size());
    }

    @Test
    void shouldNotRemoveDeliveriesThatAreNotDue() {

        RetryScheduler scheduler = new RetryScheduler();

        ScheduledDelivery d1 =
                new ScheduledDelivery("event-A", "endpoint-1", 1, 100);

        ScheduledDelivery d2 =
                new ScheduledDelivery("event-B", "endpoint-1", 1, 200);

        ScheduledDelivery d3 =
                new ScheduledDelivery("event-C", "endpoint-1", 1, 300);

        scheduler.schedule(d3);
        scheduler.schedule(d1);
        scheduler.schedule(d2);

        List<ScheduledDelivery> result = scheduler.pollDue(200);

        assertEquals(List.of(d1, d2), result);

        assertEquals(1, scheduler.size());

        assertTrue(scheduler.pollDue(250).isEmpty());

        assertEquals(1, scheduler.size());

        assertEquals(List.of(d3), scheduler.pollDue(300));

        assertEquals(0, scheduler.size());
    }

    @Test
    void keepsSameEventScheduledForDifferentEndpoints() {
        RetryScheduler scheduler = new RetryScheduler();

        ScheduledDelivery toOrders =
                new ScheduledDelivery("event-A", "endpoint-orders", 1, 100);
        ScheduledDelivery toAccounting =
                new ScheduledDelivery("event-A", "endpoint-accounting", 1, 100);

        scheduler.schedule(toOrders);
        scheduler.schedule(toAccounting);

        assertEquals(List.of(toAccounting, toOrders), scheduler.pollDue(100));
    }

    @Test
    void shouldOrderDeliveriesWithSameDueTimeByEventId() {

        RetryScheduler scheduler = new RetryScheduler();

        ScheduledDelivery d1 =
                new ScheduledDelivery("event-C", "endpoint-1", 1, 100);

        ScheduledDelivery d2 =
                new ScheduledDelivery("event-A", "endpoint-1", 1, 100);

        ScheduledDelivery d3 =
                new ScheduledDelivery("event-B", "endpoint-1", 1, 100);

        // Random insertion order
        scheduler.schedule(d1);
        scheduler.schedule(d2);
        scheduler.schedule(d3);

        List<ScheduledDelivery> result = scheduler.pollDue(100);

        assertEquals(
                List.of(d2, d3, d1),
                result
        );
    }
}
