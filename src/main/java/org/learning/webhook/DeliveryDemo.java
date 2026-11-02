package org.learning.webhook;

import org.learning.webhook.error.DeliveryException;

import java.io.IOException;

public final class DeliveryDemo {
    public static int simulateSend(String url, int simulatedStatus) throws IOException {
        if(simulatedStatus == -1){
            throw new IOException("Connection reset");
        }

        return simulatedStatus;
    }

    public static void deliver(String url, int simulatedStatus) {
        int status;
        try {
            status = simulateSend(url, simulatedStatus);
        } catch (IOException e) {
            throw new DeliveryException("Delivery to " + url + " failed: no response", -1, e);
        }

        if (status < 200 || status > 299) {
            throw new DeliveryException("Delivery to " + url + " failed with HTTP " + status, status);
        }

        System.out.println("Delivered to " + url + " (" + status + ")");
    }

    public static void main(String []args){
        int[] statuses = {200, 404, 503, -1};
        String url = "https://shop.example.com/hooks";
        for (int status : statuses) {
            try {
                deliver(url, status);
            } catch (DeliveryException e) {
                System.out.println(e.getMessage()
                        + " (retryable: " + e.isRetryable() + ", cause: " + e.getCause() + ")");
            }
        }
    }
}
