package net.cydhra.eventsystem.tests.listeners;

import net.cydhra.eventsystem.listeners.EventHandler;
import net.cydhra.eventsystem.listeners.EventType;
import net.cydhra.eventsystem.tests.events.TestEventTyped;
import net.cydhra.eventsystem.tests.response.EventReceivedInterrupt;

/**
 * This shall receive the event and throw the exception
 */
public class TestListenerTyped {
    
    @EventHandler
    @EventType(0)
    public void onTest(final TestEventTyped event) {
        throw new EventReceivedInterrupt(event.getValue());
    }
    
    @EventHandler
    public void onTestUnTyped(final TestEventTyped event) {
        throw new EventReceivedInterrupt(event.getValue());
    }
}
