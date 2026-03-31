package org.projects;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DiscordWebhook {
    private String webhookId;
    DiscordWebhook() {
        Dotenv dotenv =  Dotenv.configure()
                .directory("./")
                .load();
        String webhookId = dotenv.get("DISCORD_WEBHOOK");
        HTTPService webhookCheck = new HTTPService(webhookId);
        if (webhookCheck.isReady()) {
            System.out.println("Valid webhook!");
            this.webhookId = webhookId;
        }
        else {
            System.out.println("Invalid webhook.");
        }


    }
    public void sendMessage(String message) {
        String body = "{\"content\": ";
        body += "\"" + message + "\"}";
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .uri(URI.create(webhookId))
                    .header("Content-Type", "application/json")
                    .build();


            System.out.println(body);
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println(response.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



}
