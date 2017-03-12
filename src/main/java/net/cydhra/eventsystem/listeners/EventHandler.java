package net.cydhra.eventsystem.listeners;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation marks methods, that get called by the manager when an event of the method argument type rises.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler {
    
    /**
     * The priority of the listener method. The highest priority listeners are called first, while lowest priority are
     * called last. By default, priority is set to normal.
     *
     * @return Event Priority.
     */
    ListenerPriority priority() default ListenerPriority.NORMAL;
}
