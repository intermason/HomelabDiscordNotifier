package org.projects;


/**
 * Interface that is a broad term for different calls to services, like through HTTP, Ports, or ICMP pings.
 */
public interface Service {
    String url = "";
    String ip = "";
    int port = 0;





    /**
     * Method to check if a service is ready.
     */
    boolean isReady();
}
