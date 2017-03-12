package net.cydhra.eventsystem.tests.cases;

import net.cydhra.eventsystem.EventManager;
import net.cydhra.eventsystem.exceptions.ErrorPolicy;
import net.cydhra.eventsystem.exceptions.EventDispatchException;
import net.cydhra.eventsystem.tests.events.TestEventBase;
import net.cydhra.eventsystem.tests.events.TestEventScoped;
import net.cydhra.eventsystem.tests.listeners.TestListenerScoped;
import net.cydhra.eventsystem.tests.listeners.TestListenerScopedFail;
import net.cydhra.eventsystem.tests.response.InterruptMatcher;
import org.junit.After;
import org.junit.BeforeClass;
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
    
    @BeforeClass
    public static void setup() {
        EventManager.ERROR_POLICY = ErrorPolicy.EXCEPTION;
    }
    
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
        EventManager.callEvent(new TestEventScoped(42));
    }
    
    @Test
    public void testEventCallingWrongScope() {
        EventManager.registerListeners(testListenerFail);
        EventManager.callEvent(new TestEventBase(42));
    }
    
}
