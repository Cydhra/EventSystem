package de.cydhra.eventsystem.tests.listeners;

import de.cydhra.eventsystem.listeners.EventHandler;
import de.cydhra.eventsystem.listeners.ListenerScope;
import de.cydhra.eventsystem.tests.events.TestEventScoped;
import de.cydhra.eventsystem.tests.response.EventReceivedInterrupt;

/**
 * This shall receive the event and throw the exception
 */
public class TestListenerScoped {
    
    @EventHandler
    @ListenerScope("a.b.c")
    public void onTest(final TestEventScoped event) {
        throw new EventReceivedInterrupt(event.getValue());
    }
}
