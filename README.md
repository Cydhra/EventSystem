# EventSystem
A reflection based Java Event API

 Using this API a programmer is able to increase a system's modularity by creating loosely coupled sub-systems and set up an inter-module communication using this event system. At any place in code, an event can be created and called. The event manager will immediately call all registered listeners for this event, which can handle the event and edit ist contents (if supported by event). The execution flow will then return to the event call and continue the application flow. The event object will contain the content, the event listeners added to it.
Event handling is synchronous and optimized for quick event dispatching, so the system won’t need too much time, even with high amounts of called events. Though, since the system is using Java’s reflection API, it is recommended to not use it for extremely time critical applications or calculations.

# Compiling
The project uses maven for compiling while not using any dependencies besides Junit, so cloning the repository and calling mvn with the goal ‘package’ will get you the jar archive, which can then be used as a third party library.

# Usage
##Events
An event derives from the abstract Event class:
```Java
public SampleEvent extends Event {
	// your event contents and logic
}
```
If the same event gets called on multiple occasions, it is recommended to implement ```Typed``` to differentiate the different event origins. The interface provides a function returning an integer representing the event type. A developer may decide to define integer constants for the different event types. Event handlers can be configured to only listen to certain types of typed events.

##Handlers
An event handler is a public non-static method that is annotated with as EventHandler:
```Java
@EventHandler
public void onSampleEvent(final SampleEvent event) { … }
```
It may not define any additional parameters. The annotation ```EventHandler``` accepts an argument defining the listener’s priority. Priorities define the order, different handlers for the same event get executed. There are five priorities (highest, high, normal, low, lowest) defined and the default priority is normal.

If the handler wishes only to listen for a certain type of a typed event this annotation can be added:
```Java
@EventHandler
@Typed(2)
public void onEvent(…) {…}
```
Where 2 is the event type and should, of course, be replaced with an integer constant defined somewhere else.

##Scopes
Additionally, events can be scoped by implementing the ```Scoped``` interface, which provides a method returning a scope string. Scopes are strings in the format a.b.c and support wildcards.
An event with scope a.b will be handled by all handlers using scope a.b, a.b.* and a.*.
For more information, please read the JavaDoc of the ScopeGroup class.
