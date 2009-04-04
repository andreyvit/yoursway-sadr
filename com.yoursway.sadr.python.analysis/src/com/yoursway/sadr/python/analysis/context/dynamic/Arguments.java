package com.yoursway.sadr.python.analysis.context.dynamic;


import kilim.pausable;

import com.yoursway.sadr.python.analysis.aliasing.AliasConsumer;
import com.yoursway.sadr.python.analysis.context.lexical.PythonStaticContext;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.lang.unodes.Suffix;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public interface Arguments {
    
    @pausable
    public abstract PythonValueSet computeArgument(PythonDynamicContext dc, int index, String name,
            PythonConstruct init);
    
    @pausable
    public abstract void findRenames(Suffix suffix, PythonStaticContext sc, PythonDynamicContext dc,
            AliasConsumer aliases, int index, String name, PythonConstruct init);
    
}