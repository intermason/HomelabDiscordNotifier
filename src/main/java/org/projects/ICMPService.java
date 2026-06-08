package org.projects;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Checks if a service is available using ICMP pings.
 * <p>
 * <strong>Disclaimer:</strong> This service is only really useful if you're trying to ping a physical server or virtual machines
 * IP address. This will not work with trying to use containers that use the same IP address as its host. Please use
 * PortService instead.
 * </p>
 * @see Service
 * @see PortService
 */
public class ICMPService implements Service {
    String name;
    String ip;

    public ICMPService(String ip, String name) {
        this.name = name;
        this.ip = ip;
    }
    public ICMPService(String ip) {
        this.ip = ip;
    }

    @Override
    public boolean isReady() {
        try {
            InetAddress address = InetAddress.getByName(ip);
            System.out.println("Pinging " + ip);
            try {
                if (address.isReachable(50)) {
                    System.out.println("[ICMP] " + name + "is reachable.");
                    return true;
                } else {
                    return false;
                }
            } catch (IOException ioE) {
                System.out.println("IOException occured when trying to ping " + ip);
                System.out.println(ioE.getMessage());
            }

        } catch (UnknownHostException e) {
            System.out.println("Unknown Host. " + e.getMessage());
        }
        return false;
    }
}
