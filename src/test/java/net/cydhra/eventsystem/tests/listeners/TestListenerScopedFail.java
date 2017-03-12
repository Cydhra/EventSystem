package net.cydhra.eventsystem.tests.listeners;

import net.cydhra.eventsystem.listeners.EventHandler;
import net.cydhra.eventsystem.listeners.ListenerScope;
import net.cydhra.eventsystem.tests.events.TestEventScoped;
import net.cydhra.eventsystem.tests.response.EventReceivedInterrupt;

/**
 * These shall not receive the scoped event
 */
public class TestListenerScopedFail {
    
    @EventHandler
    @ListenerScope("a.b")
    public void onReceiveWrong(final TestEventScoped event) {
        throw new EventReceivedInterrupt(-1);
    }
    
    @EventHandler
    @ListenerScope("a.b.c.d")
    public void onReceiveWrong2(final TestEventScoped event) {
        throw new EventReceivedInterrupt(-2);
    }
}
