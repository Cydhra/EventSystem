package net.cydhra.eventsystem.tests.listeners;

import net.cydhra.eventsystem.listeners.EventHandler;
import net.cydhra.eventsystem.listeners.EventType;
import net.cydhra.eventsystem.tests.events.TestEventTyped;
import net.cydhra.eventsystem.tests.response.EventReceivedInterrupt;

/**
 * These shall not receive the scoped event
 */
public class TestListenerTypedFail {
    
    @EventHandler
    @EventType(1)
    public void onReceiveWrong(final TestEventTyped event) {
        throw new EventReceivedInterrupt(-1);
    }
    
    @EventHandler
    @EventType(3)
    public void onReceiveWrong2(final TestEventTyped event) {
        throw new EventReceivedInterrupt(-2);
    }
}
