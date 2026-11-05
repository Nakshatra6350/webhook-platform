package org.learning;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // 1 -> 10, 30 because it removes the element present at index 1, if you want to remove the value then use Integer.valueOf(1)
        List<Integer> a = new ArrayList<>(List.of(10, 20, 30));
        a.remove(1);
        System.out.println(a);

// 2 -> a
        List<String> b = new ArrayList<>(List.of("a", "b", "c"));
        for (String s : b) { if (s.equals("b")) b.remove(s); }
        System.out.println(b);

// 3 -> b, c
        List<String> c = new ArrayList<>(List.of("a", "b", "c"));
        for (String s : c) { if (s.equals("a")) c.remove(s); }
        System.out.println(c);

// 4 -> z, y
        List<String> d = Arrays.asList("x", "y");
        d.set(0, "z");
        System.out.println(d);
        d.add("w");

// 5 -> 2 \n 1
        System.out.println(new HashSet<>(List.of(new BigDecimal("1.0"), new BigDecimal("1.00"))).size());
        System.out.println(new TreeSet<>(List.of(new BigDecimal("1.0"), new BigDecimal("1.00"))).size());

// 6 -> 1 throws null pointer
        Map<String, Integer> m = new HashMap<>();
        m.put(null, 1);
        System.out.println(m.get(null));
        Map<String, Integer> cm = new ConcurrentHashMap<>();
        cm.put(null, 1);

// 7 -> false
        System.out.println(List.of(1, 2).contains(null));

// 8 -> compile with a : 2
        Map<String, Integer> dup = Map.of("a", 1, "a", 2);

// 9 -> 1, 3, 5 \n 1, 3, 5
        PriorityQueue<Integer> pq = new PriorityQueue<>(List.of(5, 1, 3));
        System.out.println(pq);
        System.out.println(pq.poll() + " " + pq.poll() + " " + pq.poll());
    }
}