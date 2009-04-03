package com.yoursway.sadr.python_v2.croco;

import java.util.Set;

import kilim.pausable;

import com.yoursway.sadr.python.constructs.PythonConstruct;
import com.yoursway.sadr.python.constructs.PythonStaticContext;
import com.yoursway.sadr.python.index.punodes.Punode;
import com.yoursway.sadr.python.index.unodes.Alias;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public interface Arguments {
    
    @pausable
    public abstract PythonValueSet computeArgument(PythonDynamicContext dc, int index, String name,
            PythonConstruct init);
    
    @pausable
    public abstract void findRenames(Punode punode, PythonStaticContext sc, PythonDynamicContext dc,
            Set<Alias> aliases, int index, String name, PythonConstruct init);
    
}