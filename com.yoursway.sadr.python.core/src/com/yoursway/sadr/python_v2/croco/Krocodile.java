package com.yoursway.sadr.python_v2.croco;

import java.util.Map;
import java.util.Set;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.constructs.PythonVariableAcceptor;
import com.yoursway.sadr.python_v2.model.ContextImpl;

public class Krocodile {
    
    private final Krocodile parent;
    
    private final PythonConstruct construct;
    
    private final ContextImpl context;
    
    Krocodile() {
        this.parent = null;
        this.construct = null;
        this.context = null;
    }
    
    public Krocodile(Krocodile parent, PythonConstruct construct, ContextImpl context) {
        this.parent = parent;
        this.construct = construct;
        this.context = context;
    }
    
    public Krocodile(Krocodile parent, PythonConstruct construct) {
        this.parent = parent;
        this.construct = construct;
        this.context = null;
    }
    
    public PythonConstruct construct() {
        return construct;
    }
    
    public RuntimeObject getActualArgument(String name) {
        if (context == null && parent != null)
            return parent.getActualArgument(name);
        return context.getActualArgument(name);
    }
    
    public void getMatchingArguments(Frog name, PythonVariableAcceptor acceptor) {
        if (context == null && parent != null) {
            parent.getMatchingArguments(name, acceptor);
        } else {
            context.findMatchingArguments(name, acceptor);
        }
    }
    
    public static final Krocodile EMPTY = new EmptyKrocodile();
    
    public Set<String> keys() {
        if (context == null && parent != null)
            return parent.keys();
        return context.keys();
    }
    
    public Map<String, RuntimeObject> entries() {
        if (context == null && parent != null)
            return parent.entries();
        return context.entries();
    }
    
    public void put(String name, RuntimeObject value) {
        if (context == null && parent != null) {
            parent.put(name, value);
        } else {
            context.put(name, value);
        }
    }
    
    @Override
    public String toString() {
        String sParent = parent != null ? parent + "\n" : "";
        String sContext = context != null ? context.toString() : "(empty)";
        return sParent + sContext;
    }
    
    public ContextImpl getContext(Scope scope) {
        if (construct.equals(scope))
            return context;
        if (parent != null)
            return parent.getContext(scope);
        return null;
    }
    
    public int size() {
        if (parent == null)
            return 1;
        return 1 + parent.size();
    }
}
