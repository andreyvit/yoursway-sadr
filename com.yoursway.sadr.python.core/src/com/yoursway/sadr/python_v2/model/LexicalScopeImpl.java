package com.yoursway.sadr.python_v2.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;

@Deprecated
public class LexicalScopeImpl implements LexicalScope {
    
    private final Map<String, RuntimeObject> namesToObjects = new HashMap<String, RuntimeObject>();
    private final LexicalScope enclosingScope;
    private final List<PythonConstruct> constructs = new LinkedList<PythonConstruct>();
    
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
    
    /**
     * Adds <code>object</code> with <code>name</code> to the scope.
     */
    public void setName(String name, RuntimeObject object) {
        namesToObjects.put(name, object);
    }
    
    public LexicalScope enclosingScope() {
        return enclosingScope;
    }
    
    public Set<String> getNames() {
        return namesToObjects.keySet();
    }
    
    public void addConstruct(PythonConstruct construct) {
        constructs.add(construct);
    }
}
