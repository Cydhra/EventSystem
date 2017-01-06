package de.cydhra.eventsystem.tests.cases;

import de.cydhra.eventsystem.EventManager;
import de.cydhra.eventsystem.exceptions.ErrorPolicy;
import de.cydhra.eventsystem.exceptions.EventDispatchException;
import de.cydhra.eventsystem.tests.events.TestEventBase;
import de.cydhra.eventsystem.tests.listeners.ProxyListenerRegistrator;
import de.cydhra.eventsystem.tests.response.InterruptMatcher;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Test with inner classes and package-private classes
 */
public class EdgeCaseEventTest {
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @BeforeClass
    public static void setup() {
        EventManager.ERROR_POLICY = ErrorPolicy.EXCEPTION;
        ProxyListenerRegistrator.registerNonPublicListener();
    }
    
    @AfterClass
    public static void shutdown() {
        ProxyListenerRegistrator.unregisterNonPublicListener();
    }
    
    @Test
    public void testEventCalling() {
        thrown.expect(EventDispatchException.class);
        thrown.expectCause(new InterruptMatcher(42));
        EventManager.callEvent(new TestEventBase(42));
    }
}
