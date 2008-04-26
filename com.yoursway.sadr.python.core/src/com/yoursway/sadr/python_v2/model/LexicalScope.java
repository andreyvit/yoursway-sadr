package com.yoursway.sadr.python_v2.model;

import java.util.Set;

/**
 * Lexical scopes in Python are function and module scopes. Lexical scopes
 * resolve names.
 * 
 * In order to correctly resolve names within a scope, 'current' statement must
 * be taken into account to deny names that are declared after that 'current'
 * statement. This should be in the area of responsibility of analysis but needs
 * some support of lexical scopes.
 */
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
     * 
     * @deprecated not in interface for the scopes are immutable for users!
     */
    @Deprecated
    void setName(String name, RuntimeObject object);
    
    /**
     * @return all names that can be looked up.
     */
    Set<String> getNames();
    
    /**
     * @return enclosing lexical scope or <code>null</code> for the outermost
     *         scope.
     */
    LexicalScope enclosingScope();
}
