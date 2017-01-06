package de.cydhra.eventsystem.tests.cases;

import de.cydhra.eventsystem.EventManager;
import de.cydhra.eventsystem.exceptions.ErrorPolicy;
import de.cydhra.eventsystem.exceptions.EventDispatchException;
import de.cydhra.eventsystem.tests.events.TestEventBase;
import de.cydhra.eventsystem.tests.listeners.TestListener;
import de.cydhra.eventsystem.tests.response.InterruptMatcher;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 */
public class PolicyTest {
    
    private static final TestListener testListener1 = new TestListener();
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @BeforeClass
    public static void setup() {
        EventManager.registerListeners(testListener1);
    }
    
    @AfterClass
    public static void shutdown() {
        EventManager.unregisterListeners(testListener1);
    }
    
    @Test
    public void testEventError() {
        // expect nothing but error output
        EventManager.ERROR_POLICY = ErrorPolicy.LOG;
        EventManager.callEvent(new TestEventBase(42));
        
        // expect dispatch exception
        thrown.expect(EventDispatchException.class);
        thrown.expectCause(new InterruptMatcher(42));
        EventManager.ERROR_POLICY = ErrorPolicy.EXCEPTION;
        EventManager.callEvent(new TestEventBase(42));
    }
}
