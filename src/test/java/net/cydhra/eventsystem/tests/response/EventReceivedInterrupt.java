package net.cydhra.eventsystem.tests.response;

/**
 * Abuse java exception to check whether events were received successfully
 */
public class EventReceivedInterrupt extends RuntimeException {
    
    public EventReceivedInterrupt(final int value) {
        super("event success: " + value);
    }
}
