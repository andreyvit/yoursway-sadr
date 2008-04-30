package com.yoursway.sadr.python.core.typeinferencing.scopes;

import org.eclipse.core.runtime.Assert;

public abstract class ChildScope extends AbstractScope {
    
    protected final Scope parent;
    
    public ChildScope(Scope parent) {
        Assert.isNotNull(parent);
        this.parent = parent;
    }
    
    @Override
    public String toString() {
        return parent.toString() + "/" + leafToString();
    }
    
    public Scope parent() {
        return parent;
    }
    
    public FileScope getOuterFile() {
        Scope t = parent;
        while (t != null) {
            if (!(t instanceof ChildScope))
                return null;
            if (t instanceof FileScope)
                return (FileScope) t;
            t = ((ChildScope) t).parent;
        }
        return null;
    }
    
    protected abstract String leafToString();
}
