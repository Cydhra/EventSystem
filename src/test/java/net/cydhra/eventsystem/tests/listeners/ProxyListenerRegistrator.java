package net.cydhra.eventsystem.tests.listeners;

import net.cydhra.eventsystem.EventManager;

/**
 * A proxy to register / unregister the non public listener
 */
public final class ProxyListenerRegistrator {
    
    private static final NonPublicListenerClass listener = new NonPublicListenerClass();
    
    private ProxyListenerRegistrator() {
        // shall not be instanced
    }
    
    public static void registerNonPublicListener() {
        EventManager.registerListeners(listener);
    }
    
    public static void unregisterNonPublicListener() {
        EventManager.unregisterListeners(listener);
    }
}
