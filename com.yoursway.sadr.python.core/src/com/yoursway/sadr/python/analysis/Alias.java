package com.yoursway.sadr.python.analysis;

import kilim.pausable;

import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonScope;
import com.yoursway.sadr.python.analysis.context.lexical.PythonStaticContext;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public final class Alias {
    
    private final Unode unode;
    private final PythonScope sc;
    private final PythonDynamicContext dc;
    
    public Alias(Unode unode, PythonScope sc, PythonDynamicContext dc) {
        if (unode == null)
            throw new NullPointerException("unode is null");
        if (sc == null)
            throw new NullPointerException("sc is null");
        if (dc == null)
            throw new NullPointerException("dc is null");
        this.unode = unode;
        this.sc = sc;
        this.dc = dc;
    }
    
    @pausable
    public PythonValueSet calculateValue(PythonDynamicContext dc) {
        return unode.calculateValue(sc.scopeContext(), dc);
    }
    
    public PythonStaticContext getStaticContext() {
        return sc.scopeContext();
    }
    
    public PythonDynamicContext getDynamicContext() {
        return dc;
    }
    
    public Unode getUnode() {
        return unode;
    }
    
    @Override
    public String toString() {
        return unode + " @" + sc;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sc == null) ? 0 : sc.hashCode());
        result = prime * result + ((unode == null) ? 0 : unode.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Alias other = (Alias) obj;
        if (sc == null) {
            if (other.sc != null)
                return false;
        } else if (!sc.equals(other.sc))
            return false;
        if (unode == null) {
            if (other.unode != null)
                return false;
        } else if (!unode.equals(other.unode))
            return false;
        return true;
    }
    
}