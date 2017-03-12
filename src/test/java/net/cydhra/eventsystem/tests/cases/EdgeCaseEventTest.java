package net.cydhra.eventsystem.tests.cases;

import net.cydhra.eventsystem.EventManager;
import net.cydhra.eventsystem.exceptions.ErrorPolicy;
import net.cydhra.eventsystem.exceptions.EventDispatchException;
import net.cydhra.eventsystem.tests.events.TestEventBase;
import net.cydhra.eventsystem.tests.listeners.ProxyListenerRegistrator;
import net.cydhra.eventsystem.tests.response.InterruptMatcher;
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
