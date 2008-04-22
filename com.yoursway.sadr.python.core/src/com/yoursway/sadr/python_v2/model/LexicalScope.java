package com.yoursway.sadr.python_v2.model;

public interface LexicalScope {
    /**
     * Looks a name up in the lexical scope according to name resolution rules.
     * 
     * @param name
     *            a name to look up.
     * @return python object model for the given name or null if the name can
     *         not be resolved.
     */
    RuntimeObject lookup(String name);
    
    /**
     * Adds <code>object</code> with <code>name</code> to the scope.
     */
    void setName(String name, RuntimeObject object);
}
