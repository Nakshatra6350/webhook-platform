package org.learning.webhook.queue;

import org.junit.jupiter.api.Test;
import org.learning.webhook.retry.ExponentialBackoff;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoundedQueueTest {
    @Test
    void shouldMaintainFifoOrder() {
        BoundedQueue<Integer> queue = new BoundedQueue<>(5);
        queue.offer(10);
        queue.offer(20);
        queue.offer(30);

        assertEquals(10, queue.poll());
        assertEquals(20, queue.poll());
        assertEquals(30, queue.poll());
    }

    @Test
    void shouldReturnFalseWhenQueueIsFull() {
        BoundedQueue<Integer> queue = new BoundedQueue<>(2);
        assertTrue(queue.offer(10));
        assertTrue(queue.offer(20));

        assertFalse(queue.offer(30));
    }

    @Test
    void shouldReturnNullWhenQueueIsEmpty(){
        BoundedQueue<Integer> queue = new BoundedQueue<>(2);
        assertNull(queue.poll());
    }

    @Test
    void shouldHandleWrapAroundCorrectly() {
        BoundedQueue<Integer> queue = new BoundedQueue<>(3);

        assertTrue(queue.offer(10));
        assertTrue(queue.offer(20));
        assertTrue(queue.offer(30));

        assertEquals(10, queue.poll());
        assertEquals(20, queue.poll());

        assertTrue(queue.offer(40));
        assertTrue(queue.offer(50));

        // Queue should now contain: 30, 40, 50.
        assertEquals(3, queue.size());

        assertEquals(30, queue.poll());
        assertEquals(40, queue.poll());
        assertEquals(50, queue.poll());

        assertTrue(queue.isEmpty());
        assertNull(queue.poll());
    }

    @Test
    void shouldDrainToCollectionOfSuperType() {
        BoundedQueue<Integer> queue = new BoundedQueue<>(5);

        queue.offer(10);
        queue.offer(20);
        queue.offer(30);

        List<Number> target = new ArrayList<>();

        int moved = queue.drainTo(target, 2);

        assertEquals(2, moved);
        assertEquals(List.of(10, 20), target);

        assertEquals(1, queue.size());
        assertEquals(30, queue.poll());

        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldThrowExceptionWhenOfferingNull() {
        BoundedQueue<Integer> queue = new BoundedQueue<>(3);

        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> queue.offer(null)
        );

        assertEquals(
                "Cannot offer null to the queue",
                exception.getMessage()
        );

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }
}
