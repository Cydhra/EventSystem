package de.cydhra.eventsystem.events;

/**
 * A typed event occurs in different versions (e.g. pre- and post-action events) and can be listened to seperatly.
 */
public interface Typed {
    
    /**
     * The event type is provided as a integer value. A listener method can specify which type of event it want to
     * listen to
     *
     * @return the event type identifier
     */
    int getType();
}
