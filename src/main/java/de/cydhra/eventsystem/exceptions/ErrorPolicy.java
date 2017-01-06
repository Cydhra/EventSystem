package de.cydhra.eventsystem.exceptions;

/**
 * This policy sets the error handling behaviour of dispatched events.
 */
public enum ErrorPolicy {
    
    /**
     * Throw a {@link EventDispatchException} on errors in dispatched events
     */
    EXCEPTION,
    
    /**
     * Output error only in log
     */
    LOG;
}
