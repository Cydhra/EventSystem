package net.cydhra.eventsystem.events;

/**
 * A scoped event only gets send to listener methods that listen to a given scope or scope group. In order to organize
 * listeners and don't invoke them, if they do not need the given event at all, a listener method can specify a scope
 * group it listens to, so if an event of the listener methods type gets called, which is not part of the scope group,
 * the method won't get called.
 */
public interface Scoped {
    
    /**
     * @return the event's scope.
     */
    String getScope();
}
