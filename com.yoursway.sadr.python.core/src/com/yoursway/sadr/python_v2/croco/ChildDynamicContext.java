package com.yoursway.sadr.python_v2.croco;

public abstract class ChildDynamicContext extends PythonDynamicContext {
    
    protected final PythonDynamicContext parent;
    
    public ChildDynamicContext(PythonDynamicContext parent) {
        if (parent == null)
            throw new NullPointerException("parent is null");
        this.parent = parent;
    }
    
    @Override
    public final String toString() {
        return parent + "\n" + shallowToString();
    }
    
    protected abstract String shallowToString();
    
    @Override
    public final PythonDynamicContext unwind() {
        return parent;
    }
    
}
