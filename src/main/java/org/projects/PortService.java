package org.projects;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;


/**
 * Checks a service is available by using its ip address and port number.
 * @see Service
 */
public class PortService implements Service {
    String name;
    String ip;
    int port;

    PortService(String ip, int port, String name) {
        this.name = name;
        this.ip = ip;
        if (port < 65535) {
            this.port = port;
        }
        else {
            System.out.println("Invalid port number. Defaulting to 443");
            this.port = 443;
        }
    }
    PortService(String ip, int port) {
        this.ip = ip;
        if (port < 65535) {
            this.port = port;
        }
        else {
            System.out.println("Invalid port number. Defaulting to 443");
            this.port = 443;
        }
    }

    @Override
    public boolean isReady() {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(ip, port), 3000);
            System.out.println("[PORT] " + name + " is reachable.");
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
