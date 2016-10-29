package de.cydhra.eventsystem.tests.cases;

import de.cydhra.eventsystem.EventManager;
import de.cydhra.eventsystem.exceptions.EventDispatchException;
import de.cydhra.eventsystem.tests.events.TestEventTyped;
import de.cydhra.eventsystem.tests.listeners.TestListenerTyped;
import de.cydhra.eventsystem.tests.listeners.TestListenerTypedFail;
import de.cydhra.eventsystem.tests.response.InterruptMatcher;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 */
public class TypeTest {
    
    private static final TestListenerTyped testListener = new TestListenerTyped();
    private static final TestListenerTypedFail testListenerFail = new TestListenerTypedFail();
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @After
    public void afterTest() {
        // unregister everything
        EventManager.unregisterListeners(testListener);
        EventManager.unregisterListeners(testListenerFail);
    }
    
    @Test
    public void testEventCalling() {
        EventManager.registerListeners(testListener);
        thrown.expect(EventDispatchException.class);
        thrown.expectCause(new InterruptMatcher(42));
        EventManager.callEvent(new TestEventTyped(42));
    }
    
    @Test
    public void testEventCallingWrongType() {
        EventManager.registerListeners(testListenerFail);
        EventManager.callEvent(new TestEventTyped(42));
    }
    
}
