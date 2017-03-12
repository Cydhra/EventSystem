package net.cydhra.eventsystem.tests.listeners;

import net.cydhra.eventsystem.listeners.EventHandler;
import net.cydhra.eventsystem.tests.events.TestEventBase;
import net.cydhra.eventsystem.tests.response.EventReceivedInterrupt;

/**
 *
 */
class NonPublicListenerClass {
    
    @EventHandler
    public void onTest(final TestEventBase event) {
        throw new EventReceivedInterrupt(event.getValue());
    }
}
