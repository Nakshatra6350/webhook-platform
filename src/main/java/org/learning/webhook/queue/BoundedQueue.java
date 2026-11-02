package org.learning.webhook.queue;

import java.util.Collection;
import java.util.Objects;

public final class BoundedQueue<T> {
    private final Object[] items;   // the storage
    private int head;               // index of the next item to poll
    private int tail;               // index where the next offer goes
    private int count;

    public BoundedQueue(int capacity) {
        if(capacity <= 0){
            throw new IllegalArgumentException("Capacity must be greater than 0, got: " + capacity);
        }
        this.items = new Object[capacity];
    }

    public boolean offer(T item){
        if(item == null){
            throw new NullPointerException("Cannot offer null to the queue");
        }

        if(count == items.length){
            return false;
        }

        items[tail] = item;
        tail++;
        if(tail == items.length){
            tail = 0;
        }
        count++;
        return true;
    }

    @SuppressWarnings("unchecked")
    public T poll() {
        if(count == 0) return null;
        T item = (T) items[head]; // because it is not new T[], it is an object type and we can cast it safely
        items[head] = null;
        head++;
        if(head == items.length){
            head = 0;
        }
        count--;
        return item;
    }

    public int size(){
        return count;
    }

    public boolean isEmpty(){
        return count == 0;
    }

    public int drainTo(Collection<? super T> target, int maxItems){
        Objects.requireNonNull(target, "target");
        if (maxItems < 0) {
            throw new IllegalArgumentException("maxItems must be >= 0, got: " + maxItems);
        }
        int moved = 0;
        while (moved < maxItems && count > 0) {
            target.add(poll());
            moved++;
        }
        return moved;
    }
}
