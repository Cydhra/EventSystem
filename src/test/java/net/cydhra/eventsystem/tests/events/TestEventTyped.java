package net.cydhra.eventsystem.tests.events;

import net.cydhra.eventsystem.events.Typed;

/**
 *
 */
public class TestEventTyped extends TestEventBase implements Typed {
    
    public TestEventTyped(final int value) {
        super(value);
    }
    
    @Override public int getType() {
        return 0;
    }
}
