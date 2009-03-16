package com.yoursway.sadr.python_v2.croco;

import java.util.Map;
import java.util.Set;

import com.yoursway.sadr.core.constructs.DynamicContext;
import com.yoursway.sadr.python.constructs.PythonConstruct;
import com.yoursway.sadr.python.constructs.PythonStaticContext;
import com.yoursway.sadr.python.objects.ContextImpl;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;

public class PythonDynamicContext implements DynamicContext {
    
    private final PythonDynamicContext parent;
    
    private final PythonConstruct construct;
    
    private final ContextImpl context;
    
    PythonDynamicContext() {
        this.parent = null;
        this.construct = null;
        this.context = null;
    }
    
    public PythonDynamicContext(PythonDynamicContext parent, PythonConstruct construct, ContextImpl context) {
        this.parent = parent;
        this.construct = construct;
        this.context = context;
    }
    
    public PythonDynamicContext(PythonDynamicContext parent, PythonConstruct construct) {
        this.parent = parent;
        this.construct = construct;
        this.context = null;
    }
    
    public PythonConstruct construct() {
        return construct;
    }
    
    public PythonValue getActualArgument(String name) {
        if (context == null && parent != null)
            return parent.getActualArgument(name);
        return context.getActualArgument(name);
    }
    
    public static final PythonDynamicContext EMPTY = new EmptyKrocodile();
    
    public Set<String> keys() {
        if (context == null && parent != null)
            return parent.keys();
        return context.keys();
    }
    
    public Map<String, PythonValue> entries() {
        if (context == null && parent != null)
            return parent.entries();
        return context.entries();
    }
    
    public void put(String name, PythonValue value) {
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
    
    public ContextImpl getContext(PythonStaticContext staticContext) {
        if (construct.equals(staticContext))
            return context;
        if (parent != null)
            return parent.getContext(staticContext);
        return null;
    }
    
    public int size() {
        if (parent == null)
            return 1;
        return 1 + parent.size();
    }
}
