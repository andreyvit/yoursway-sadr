package com.yoursway.sadr.python_v2.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LexicalScopeImpl implements LexicalScope {
    
    private final Map<String, RuntimeObject> namesToObjects = new HashMap<String, RuntimeObject>();
    private final LexicalScope enclosingScope;
    
    /**
     * @param enclosingScope
     *            should only be <code>null</code> for the outermost scopes
     *            (such as built-ins scope).
     */
    public LexicalScopeImpl(LexicalScope enclosingScope) {
        this.enclosingScope = enclosingScope;
    }
    
    public RuntimeObject lookup(String name) {
        return namesToObjects.get(name);
    }
    
    public void setName(String name, RuntimeObject object) {
        namesToObjects.put(name, object);
    }
    
    public LexicalScope enclosingScope() {
        return enclosingScope;
    }
    
    public Set<String> getNames() {
        return namesToObjects.keySet();
    }
    
}
