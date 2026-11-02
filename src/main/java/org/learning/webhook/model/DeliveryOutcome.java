package org.learning.webhook.model;

public enum DeliveryOutcome {
    SUCCESS(true),
    RETRY(false),
    GIVE_UP(true);

    private final boolean terminal;

    DeliveryOutcome(boolean terminal) {
        this.terminal = terminal;
    }

    public boolean isTerminal() {
        return terminal;
    }
}
