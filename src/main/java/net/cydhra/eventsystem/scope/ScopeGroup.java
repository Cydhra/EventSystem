package net.cydhra.eventsystem.scope;

import java.util.regex.Pattern;

/**
 * A scope group is a group of scopes that an event handler listens to. A scoped event has a specified scope (e.g.
 * 'A.B'). Every event handler, that listens for 'A.B', 'A.B.*' or a superior scope group like 'A.*' is able to receive
 * the event.
 * <p>
 * In general, a group must match the pattern {@value #GROUP_REGEX}
 * <p>
 * This class is specifically for dispatching scoped events internally, but can also be used outside to manager scopes
 */
public class ScopeGroup {
    
    private static final String GROUP_REGEX = "((\\.?[a-zA-Z0-9])+(\\.\\*)?|\\*)";
    private static final Pattern GROUP_PATTERN = Pattern.compile(GROUP_REGEX);
    
    private final Group group;
    
    public ScopeGroup(final String scope) {
        if (!GROUP_PATTERN.matcher(scope).matches())
            throw new IllegalArgumentException("scope pattern " + scope + " is not a valid scope group");
        
        this.group = compile(scope);
    }
    
    /**
     * Compiles the scope group, so it can be handled easier
     */
    private static Group compile(final String scopeString) {
        String[] scopeGroupNames = scopeString.split("\\.");
        
        Group newGroup = null;
        for (int i = scopeGroupNames.length - 1; i >= 0; i--) {
            newGroup = new Group(scopeGroupNames[i], newGroup);
        }
        
        return newGroup;
    }
    
    /**
     * Determines whether a scope lies within this scope group
     *
     * @param scope the scope that shall be checked
     *
     * @return true, if the scope is within this group definition
     */
    public boolean containsScope(final String scope) {
        if (scope.contains("*"))
            throw new IllegalArgumentException(
                    "Illegal scope format: " + scope + ". A scope may not contain any wildcards");
        
        Group matcherGroup = compile(scope);
        return this.group.containsGroup(matcherGroup);
    }
    
    /**
     * A compiled scope group expression
     */
    private static class Group {
        private final String groupName;
        private final Group subGroup;
        
        /**
         * A scope group has a name and may have a sub group
         *
         * @param groupName name of the group or wildcard symbol *
         * @param subGroup  sub group or null, if this is the deepest group already
         */
        Group(final String groupName, final Group subGroup) {
            this.groupName = groupName;
            this.subGroup = subGroup;
        }
        
        /**
         * Recursive check wether another scope (group) is contained in this group
         *
         * @param group the scope (group) that is checked
         *
         * @return true, if the other group lies within this group
         */
        private boolean containsGroup(final Group group) {
            // if this is a wild card end of a group definition
            if (this.groupName.equalsIgnoreCase("*")) {
                return true;
            }
            
            // if sub group for the matcher group was provided and this isn't the wilcard end of a group def
            if (group == null)
                return false;
            
            // if this the end of group def, but not a wildcard, a deeper group doesn't match
            if (this.subGroup == null) {
                return group.subGroup == null;
            }
            
            return groupName.equalsIgnoreCase(group.groupName) && this.subGroup.containsGroup(group.subGroup);
        }
    }
}
