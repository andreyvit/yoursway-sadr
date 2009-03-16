package com.yoursway.sadr.python_v2.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;

@Deprecated
public class LexicalScopeImpl implements LexicalScope {
    
    private final Map<String, PythonObject> namesToObjects = new HashMap<String, PythonObject>();
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
    
    public PythonObject lookup(String name) {
        return namesToObjects.get(name);
    }
    
    /**
     * Adds <code>object</code> with <code>name</code> to the scope.
     */
    public void setName(String name, PythonObject object) {
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
