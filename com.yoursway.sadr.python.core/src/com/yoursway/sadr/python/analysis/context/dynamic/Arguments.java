package com.yoursway.sadr.python.analysis.context.dynamic;

import java.util.Set;

import kilim.pausable;

import com.yoursway.sadr.python.analysis.Alias;
import com.yoursway.sadr.python.analysis.context.lexical.PythonStaticContext;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.lang.unodes.punodes.Punode;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public interface Arguments {
    
    @pausable
    public abstract PythonValueSet computeArgument(PythonDynamicContext dc, int index, String name,
            PythonConstruct init);
    
    @pausable
    public abstract void findRenames(Punode punode, PythonStaticContext sc, PythonDynamicContext dc,
            Set<Alias> aliases, int index, String name, PythonConstruct init);
    
}