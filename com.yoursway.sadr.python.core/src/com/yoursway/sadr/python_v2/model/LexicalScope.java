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
 * 
 * Lexical scopes should be implemented as dictionaries (hence are allowed to be
 * dynamically changed) but must take hierarchical structure into account for
 * name resolution.
 * 
 * @deprecated but may be used in less dynamic languages as a building block.
 */
@Deprecated
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
     * @return all names that can be looked up.
     */
    Set<String> getNames();
    
    /**
     * @return enclosing lexical scope or <code>null</code> for the outermost
     *         scope.
     */
    LexicalScope enclosingScope();
    
}
