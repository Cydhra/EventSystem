package de.cydhra.eventsystem.tests.listeners;

import de.cydhra.eventsystem.listeners.EventHandler;
import de.cydhra.eventsystem.tests.response.EventReceivedInterrupt;
import de.cydhra.eventsystem.tests.events.TestEventBase;

/**
 *
 */
public class TestListener {
    
    @EventHandler
    public void onTest(final TestEventBase event) {
        throw new EventReceivedInterrupt(event.getValue());
    }
}
