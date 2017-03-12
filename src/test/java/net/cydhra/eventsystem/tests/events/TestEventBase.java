package net.cydhra.eventsystem.tests.events;

import net.cydhra.eventsystem.events.Event;

/**
 * A test event class
 */
public class TestEventBase extends Event {
    private final int value;
    
    public TestEventBase(final int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
}
