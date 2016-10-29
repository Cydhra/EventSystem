package de.cydhra.eventsystem.tests.cases;

import de.cydhra.eventsystem.EventManager;
import de.cydhra.eventsystem.exceptions.EventDispatchException;
import de.cydhra.eventsystem.tests.events.TestEventBase;
import de.cydhra.eventsystem.tests.events.TestEventScoped;
import de.cydhra.eventsystem.tests.listeners.TestListenerScoped;
import de.cydhra.eventsystem.tests.listeners.TestListenerScopedFail;
import de.cydhra.eventsystem.tests.response.InterruptMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 */
public class ScopeTest {
    
    private static final TestListenerScoped testListener = new TestListenerScoped();
    private static final TestListenerScopedFail testListenerFail = new TestListenerScopedFail();
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    
    @Test
    public void testEventCalling() {
        EventManager.registerListeners(testListener);
        thrown.expect(EventDispatchException.class);
        thrown.expectCause(new InterruptMatcher(42));
        EventManager.callEvent(new TestEventScoped(42));
        EventManager.unregisterListeners(testListener);
    }
    
    @Test
    public void testEventCallingWrongScope() {
        EventManager.registerListeners(testListenerFail);
        EventManager.callEvent(new TestEventBase(42));
        EventManager.registerListeners(testListenerFail);
    }
    
}
