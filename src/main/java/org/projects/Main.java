package org.projects;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        List<JSONServiceObject> serviceObj = new ArrayList<>();
        List<Service> services = new ArrayList<>();
        DiscordWebhook webhook = new DiscordWebhook();
        try {
            serviceObj = mapper.readValue(new File("services.json"), new TypeReference<List<JSONServiceObject>>() {
            });

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        for (JSONServiceObject service : serviceObj) {
            switch (service.type) {
                case "http":
                    HTTPService httpServ = new HTTPService(service.url, service.name, service.quiet);
                    services.add(httpServ);
                    break;
                case "icmp":
                    ICMPService icmpServ = new ICMPService(service.ip, service.name);
                    services.add(icmpServ);
                    break;
                case "port":
                    PortService portServ = new PortService(service.ip, service.port, service.name);
                    services.add(portServ);
                    break;
                default:
                    System.out.println("Unknown service type " + service.type);
                    throw new Error("Unknown service type " + service.type);

            }
        }
        for (Service service : services) {
            if (service.isReady()) {
                webhook.sendMessage(service.getName() + " is online.");

            } else {
                webhook.sendMessage(service.getName() + " is offline.");
            }


        }
    }

}
