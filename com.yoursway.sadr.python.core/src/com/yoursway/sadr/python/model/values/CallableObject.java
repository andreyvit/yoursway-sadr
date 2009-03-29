package com.yoursway.sadr.python.model.values;

import java.util.List;

import kilim.pausable;

import com.yoursway.sadr.python.constructs.PythonConstruct;
import com.yoursway.sadr.python.index.unodes.Bnode;
import com.yoursway.sadr.python.model.PassedArgumentInfo;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetBuilder;

public interface CallableObject {
    
    public abstract String name();
    
    // may return null
    public PythonConstruct getDecl();
    
    @pausable
    public void call(PythonDynamicContext dc, PythonValueSetBuilder builder);
    
    void computeArgumentAliases(PassedArgumentInfo info, List<Bnode> unodes);
    
}
