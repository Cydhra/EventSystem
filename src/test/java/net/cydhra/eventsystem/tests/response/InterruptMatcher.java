package net.cydhra.eventsystem.tests.response;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.lang.reflect.InvocationTargetException;

/**
 * Matches interrupts caused by successfully received events
 */
public class InterruptMatcher extends BaseMatcher<InvocationTargetException> {
    
    private final int value;
    
    public InterruptMatcher(final int value) {
        this.value = value;
    }
    
    @Override public boolean matches(final Object o) {
        return o instanceof InvocationTargetException &&
                ((InvocationTargetException) o).getCause() instanceof EventReceivedInterrupt &&
                ((InvocationTargetException) o).getCause().getMessage()
                        .contains("event success: " + value);
    }
    
    @Override public void describeTo(final Description description) {
    }
}
