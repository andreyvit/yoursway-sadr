package com.yoursway.sadr.python.index.unodes;

import kilim.pausable;

import com.yoursway.sadr.python.constructs.PythonScope;
import com.yoursway.sadr.python.constructs.PythonStaticContext;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public final class Bnode {
    
    private final Unode unode;
    private final PythonScope sc;
    
    public Bnode(Unode unode, PythonScope sc) {
        if (unode == null)
            throw new NullPointerException("unode is null");
        if (sc == null)
            throw new NullPointerException("sc is null");
        this.unode = unode;
        this.sc = sc;
    }
    
    @pausable
    public PythonValueSet calculateValue(PythonDynamicContext dc) {
        return unode.calculateValue(sc.scopeContext(), dc, sc.currentScopes());
    }
    
    public PythonStaticContext getStaticContext() {
        return sc.scopeContext();
    }
    
    public Unode getUnode() {
        return unode;
    }
    
}
