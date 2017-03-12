package net.cydhra.eventsystem.listeners;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A typed listener only listens to a specific type of a typed event. The type is defined by an integer constant, which
 * identifies the type
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventType {
    
    /**
     * @return the type identifier that shall be listened to
     */
    int value();
}
