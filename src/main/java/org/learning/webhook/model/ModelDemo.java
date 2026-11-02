package org.learning.webhook.model;

import java.util.HashSet;
import java.util.List;

public class ModelDemo {
    public static void main(String[] args) {

        WebhookEndpoint e1 = new WebhookEndpoint(
                "ep_custom",
                "https://order.example.com/webhooks",
                "whsec_zyxwvutsrqponm",
                10);

        WebhookEndpoint e2 = new WebhookEndpoint(
                "ep_custom",
                "https://payment.example.com/webhooks",
                "whsec_zyxwvutsrqponm",
                10);

        WebhookEvent ev1 = new WebhookEvent(
                "ep_custom",
                "https://order.example.com/webhooks",
                "whsec_zyxwvutsrqponz",
                10);

        WebhookEvent ev2 = new WebhookEvent(
                "ep_custom",
                "https://order.example.com/webhooks",
                "whsec_zyxwvutsrqponz",
                10);

        HashSet<WebhookEndpoint> set = new HashSet<>();
        set.add(e1);
        set.add(e2);

        System.out.println("Set Size : " + set.size());

        System.out.println("Is ev1 and ev2 equal : " + ev1.equals(ev2));
        System.out.println("ev1 hashcode : " + ev1.hashCode());
        System.out.println("ev2 hashcode : " + ev2.hashCode());

        System.out.println("Printing webhookendpoint : " + e1);

        System.out.println("Printing status's delivery outcomes");
        for (Integer i : List.of(200, 301, 404, 408, 429, 500, 503)) {
            DeliveryOutcome status = StatusClassifier.decide(i);
            System.out.println(i + " -> " + status + " (terminal: " + status.isTerminal() + ")");
        }

    }
}
