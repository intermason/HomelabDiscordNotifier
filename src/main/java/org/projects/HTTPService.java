package org.projects;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;

/**
 * Checks if a service is available by using its HTTP/HTTPS URL.
 * Can handle redirects.
 * @see Service
 */
public class HTTPService implements Service {
    String name;
    String url;
    boolean quiet;

    HTTPService(String url, String name, boolean quiet) {
        this.url = url;
        this.name = name;
        this.quiet = quiet;
    }

    HTTPService(String url, String name) { this.url = url; this.name = name; this.quiet = false; }

    HTTPService(String url, boolean quiet) {
        this.url = url;
        this.name = "Unknown service";
        this.quiet = quiet;

    }

    public HTTPService(String url) {
        this.url = url;
        this.name = "Unknown service";
        this.quiet = false;
    }


    @Override
    public boolean isReady() {
        int maxAttempts = 5;
        int delaySeconds = 3;

        for (int attempts = 0; attempts <= maxAttempts; attempts++) {
            try (HttpClient client = HttpClient.newBuilder()
                    .followRedirects(HttpClient.Redirect.ALWAYS)
                    .build()) {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .GET()
                        .timeout(Duration.ofSeconds(5))
                        .build();
                HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
                if (!quiet) System.out.println("[HTTP]\t" + name + " status code: " + response.statusCode());
                return response.statusCode() == 200;
            } catch (HttpTimeoutException e) {
                System.out.println(attempts + " failed, retrying...");
                if (attempts < maxAttempts) {
                    try {
                        Thread.sleep(delaySeconds * 1000);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        return false;
                    }
                }


            } catch (IOException | InterruptedException e) {
                System.out.println("IOException or InterruptedException found. " + e.getMessage());
            }
        }
        return false;
    }

    @Override
    public String getName() {
        return name;
    }
}
