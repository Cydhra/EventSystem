package net.cydhra.eventsystem.tests.events;

import net.cydhra.eventsystem.events.Scoped;

/**
 *
 */
public class TestEventScoped extends TestEventBase implements Scoped {
    
    public TestEventScoped(final int value) {
        super(value);
    }
    
    @Override public String getScope() {
        return "a.b.c";
    }
}
