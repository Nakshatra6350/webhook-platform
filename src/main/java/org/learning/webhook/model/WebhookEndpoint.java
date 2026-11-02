package org.learning.webhook.model;

import org.learning.webhook.error.InvalidEndpointException;

public final class WebhookEndpoint {

    private static int idSequence = 0;
    private static int created = 0;

    private final String id;
    private final String url;
    private final String secret;
    private final int maxRetries;

    public WebhookEndpoint(String id, String url, String secret, int maxRetries) {

        if (id == null || id.isBlank()) {
            throw new InvalidEndpointException("Id is required");
        }

        if (url == null || !url.startsWith("https://")) {
            throw new InvalidEndpointException(
                    "URL must start with https://, got: " + url);
        }

        if (secret == null) {
            throw new InvalidEndpointException("Secret is required");
        }

        if (secret.length() < 16) {
            throw new InvalidEndpointException(
                    "Secret must be at least 16 characters, got length: "
                            + secret.length());
        }

        if (maxRetries < 0 || maxRetries > 20) {
            throw new InvalidEndpointException(
                    "maxRetries must be between 0 and 20, got: "
                            + maxRetries);
        }

        this.id = id;
        this.url = url;
        this.secret = secret;
        this.maxRetries = maxRetries;

        created++;
    }

    public WebhookEndpoint(String url, String secret) {
        this(nextId(), url, secret, 5);
    }

    @Override
    public boolean equals(Object o) {                   // parameter type MUST be Object
        if (this == o) return true;                     // fast path: same object
        if (!(o instanceof WebhookEndpoint other)) return false;  // also handles null: null instanceof X is false
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "WebhookEndpoint{id=" + id + ", url=" + url + ", secret=" + maskedSecret() + "}";
    }

    private static String nextId() {
        idSequence++;
        return "ep_" + idSequence;
    }

    public static int createdCount() {
        return created;
    }

    public String maskedSecret() {
        return secret.substring(0, 4) + "****";
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}
