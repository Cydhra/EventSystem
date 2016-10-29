package de.cydhra.eventsystem.listeners;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies the listener for a certain scope (e.g. A.B) or scope group (e.g. A.*)
 *
 * @{@link de.cydhra.eventsystem.scope.Scope}
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ListenerScope {
    
    /**
     * @return the handler's scope or scope group
     */
    String value();
}
