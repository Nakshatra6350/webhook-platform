package org.learning.webhook.cache;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LruCacheTest {
    @Test
    void shouldEvictLeastRecentlyUsedEntry() {

        LruCache<String, Integer> cache = new LruCache<>(2);

        cache.put("A", 1);
        cache.put("B", 2);

        assertEquals(2, cache.size());

        assertEquals(1, cache.get("A"));

        cache.put("C", 3);

        assertEquals(2, cache.size());

        assertTrue(cache.containsKey("A"));
        assertFalse(cache.containsKey("B"));
        assertTrue(cache.containsKey("C"));

        assertEquals(1, cache.get("A"));
        assertEquals(3, cache.get("C"));
    }
}
