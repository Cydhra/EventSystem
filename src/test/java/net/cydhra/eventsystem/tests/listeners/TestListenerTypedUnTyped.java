package net.cydhra.eventsystem.tests.listeners;

import net.cydhra.eventsystem.listeners.EventHandler;
import net.cydhra.eventsystem.tests.events.TestEventTyped;
import net.cydhra.eventsystem.tests.response.EventReceivedInterrupt;

/**
 * A Listener for a typed event with untyped listener
 */
public class TestListenerTypedUnTyped {
    
    @EventHandler
    public void onTestUnTyped(final TestEventTyped event) {
        throw new EventReceivedInterrupt(event.getValue());
    }
}
