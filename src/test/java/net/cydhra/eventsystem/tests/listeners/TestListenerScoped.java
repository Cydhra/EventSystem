package net.cydhra.eventsystem.tests.listeners;

import net.cydhra.eventsystem.listeners.EventHandler;
import net.cydhra.eventsystem.listeners.ListenerScope;
import net.cydhra.eventsystem.tests.events.TestEventScoped;
import net.cydhra.eventsystem.tests.response.EventReceivedInterrupt;

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
