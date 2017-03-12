package de.cydhra.eventsystem;

import de.cydhra.eventsystem.events.Event;
import de.cydhra.eventsystem.events.Scoped;
import de.cydhra.eventsystem.events.Typed;
import de.cydhra.eventsystem.exceptions.ErrorPolicy;
import de.cydhra.eventsystem.exceptions.EventDispatchException;
import de.cydhra.eventsystem.listeners.EventHandler;
import de.cydhra.eventsystem.listeners.EventType;
import de.cydhra.eventsystem.listeners.ListenerPriority;
import de.cydhra.eventsystem.listeners.ListenerScope;
import de.cydhra.eventsystem.scope.ScopeGroup;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Registers and manages listeners and handles event dispatching
 */
public final class EventManager {
    
    /**
     * The error policy determines how exceptions on dispatched events will be handled.
     */
    public static ErrorPolicy ERROR_POLICY = ErrorPolicy.LOG;
    
    private static final HashMap<Class<? extends Event>, CopyOnWriteArrayList<Listener>> registeredListeners =
            new HashMap<>();
    private static final String GLOBAL_SCOPE = "*";
    
    /**
     * This is a static manager for listeners and shall not be instanced
     */
    private EventManager() {
        
    }
    
    /**
     * Identify and registers all {@link de.cydhra.eventsystem.listeners.EventHandler} methods in the class of given
     * Object instance. The instance will be saved, because it is used to invoke the listeners, thus the listeners
     * cannot be static and the object won't get destroyed by garbage collection. Use it to unregister the listeners
     *
     * @param listenerClassInstance An object of a class containing event handlers
     */
    public static void registerListeners(final Object listenerClassInstance) {
        for (Method method : listenerClassInstance.getClass().getMethods()) {
            if (!method.isAnnotationPresent(EventHandler.class)) {
                continue;
            }
            
            // illegal parameter count
            if (method.getParameterCount() != 1) {
                System.err.println("Ignoring illegal event handler: " + method.getName() +
                        ": Wrong number of arguments (required: 1)");
                continue;
            }
            
            // illegal parameter
            if (!Event.class.isAssignableFrom(method.getParameterTypes()[0])) {
                System.err.println("Ignoring illegal event handler: " + method.getName() + ": Argument must extend " +
                        Event.class.getName());
                continue;
            }
            
            @SuppressWarnings("unchecked") Class<? extends Event> eventType =
                    (Class<? extends Event>) method.getParameterTypes()[0];
            
            String scope = GLOBAL_SCOPE;
            if (method.isAnnotationPresent(ListenerScope.class)) {
                if (!Scoped.class.isAssignableFrom(eventType)) {
                    System.err.println("Ignoring illegal event handler: " + method.getName() +
                            ": Handler is scoped, but event not.");
                    continue;
                }
                
                scope = method.getAnnotation(ListenerScope.class).value();
            }
            
            int listenedEventType = -1;
            if (method.isAnnotationPresent(EventType.class)) {
                if (!Typed.class.isAssignableFrom(eventType)) {
                    System.err.println("Ignoring illegal event handler: " + method.getName() +
                            ": Handler is typed, but event not.");
                    continue;
                }
                
                listenedEventType = method.getAnnotation(EventType.class).value();
            }
            
            ListenerPriority priority   = method.getAnnotation(EventHandler.class).priority();
            ScopeGroup       scopeGroup = new ScopeGroup(scope);
            
            Listener listener = new Listener(listenerClassInstance, method, scopeGroup, priority, listenedEventType);
            addListener(eventType, listener);
        }
    }
    
    /**
     * Adds a listener to the map of registered listeners
     *
     * @param eventType the event type that listener should handle
     * @param listener  the listener method
     */
    private static void addListener(final Class<? extends Event> eventType, final Listener listener) {
        if (!registeredListeners.containsKey(eventType))
            registeredListeners.put(eventType, new CopyOnWriteArrayList<>());
        
        registeredListeners.get(eventType).add(listener);
    }
    
    /**
     * Unregisteres all event handlers associated with this object
     *
     * @param listenerClassInstance An object of a class containing even handlers that has been registered at the event handler
     */
    public static void unregisterListeners(final Object listenerClassInstance) {
        for (CopyOnWriteArrayList<Listener> listenerList : registeredListeners.values()) {
            for (int i = 0; i < listenerList.size(); i++) {
                if (listenerList.get(i).listenerClassInstance == listenerClassInstance) {
                    listenerList.remove(i);
                    i -= 1;
                }
            }
        }
    }
    
    /**
     * Dispatch an event by calling any listener responsible for the given event
     *
     * @param event An arbitrary instance of any subclass of {@link Event}
     */
    public static void callEvent(final Event event) {
        boolean scoped = false, typed = false;
        
        String scope = "";
        int    type  = -1;
        
        if (event instanceof Scoped) {
            scoped = true;
            scope = ((Scoped) event).getScope();
        }
        
        if (event instanceof Typed) {
            typed = true;
            type = ((Typed) event).getType();
        }
        
        dispatchEvent(scoped, scope, typed, type, event, ListenerPriority.HIGHEST);
        dispatchEvent(scoped, scope, typed, type, event, ListenerPriority.HIGH);
        dispatchEvent(scoped, scope, typed, type, event, ListenerPriority.NORMAL);
        dispatchEvent(scoped, scope, typed, type, event, ListenerPriority.LOW);
        dispatchEvent(scoped, scope, typed, type, event, ListenerPriority.LOWEST);
    }
    
    /**
     * Checks for all listeners of an event class, whether they shall receive the event with given parameters
     *
     * @param scoped   whether the event is scoped
     * @param scope    the event's scope
     * @param typed    whether the event is typed
     * @param type     the event's type
     * @param event    the event
     * @param priority the current dispatched priority
     */
    private static void dispatchEvent(final boolean scoped, final String scope, final boolean typed, final int type,
            final Event event, final ListenerPriority priority) {
        CopyOnWriteArrayList<Listener> listeners = registeredListeners.get(event.getClass());
        if (listeners != null) {
            for (Listener listener : listeners) {
                if (!scoped || listener.scopeGroup.containsScope(scope)) {
                    if (!typed || listener.listenedEventType == type) {
                        if (listener.priority == priority) {
                            try {
                                listener.listenerMethod.setAccessible(true);
                                listener.listenerMethod.invoke(listener.listenerClassInstance, event);
                            } catch (IllegalAccessException e) {
                                System.err.print("Could not access event handler method:");
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                
                                // depending on error policy, throw an exception or just log and ignore
                                switch (ERROR_POLICY) {
                                    case EXCEPTION:
                                        throw new EventDispatchException("Could not dispatch event to handler " +
                                                listener.listenerMethod.getName(), e);
                                    case LOG:
                                        System.err.println("Could not dispatch event to handler " +
                                                listener.listenerMethod.getName());
                                        e.printStackTrace();
                                        break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    /**
     * A container for listener methods
     */
    private static class Listener {
        private final Object listenerClassInstance;
        private final Method listenerMethod;
        private final ScopeGroup scopeGroup;
        private final ListenerPriority priority;
        private final int listenedEventType;
        
        /**
         * Create a container for listener methods with all necessary meta data
         *
         * @param listenerClassInstance instance of the listener class
         * @param listenerMethod        the event handler method
         * @param scopeGroup            the handler scope (group)
         * @param priority              the listener priority
         * @param listenedEventType     the event type for typed events
         */
        private Listener(final Object listenerClassInstance, final Method listenerMethod, final ScopeGroup scopeGroup,
                final ListenerPriority priority, final int listenedEventType) {
            this.listenerClassInstance = listenerClassInstance;
            this.listenerMethod = listenerMethod;
            this.scopeGroup = scopeGroup;
            this.priority = priority;
            this.listenedEventType = listenedEventType;
        }
    }
}
