package de.cydhra.eventsystem.tests.listeners;

import de.cydhra.eventsystem.listeners.EventHandler;
import de.cydhra.eventsystem.listeners.EventType;
import de.cydhra.eventsystem.tests.events.TestEventTyped;
import de.cydhra.eventsystem.tests.response.EventReceivedInterrupt;

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
