package com.yoursway.sadr.python.analysis.lang.unodes;

import java.util.Collection;
import java.util.Set;

import kilim.pausable;

import com.yoursway.sadr.python.analysis.Alias;
import com.yoursway.sadr.python.analysis.PythonAnalHelpers;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonStaticContext;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.lang.unodes.punodes.Punode;

public abstract class AbstractIndexableUnode extends Unode {
    
    @Override
    public boolean isIndexable() {
        return true;
    }
    
    @Override
    @pausable
    public void findRenames(Punode punode, PythonStaticContext sc, PythonDynamicContext dc, Set<Alias> aliases) {
        if (isIndexable()) {
            Collection<PythonConstruct> valuesAssignedToPunodeHead = PythonAnalHelpers.findRenames(new Alias(punode.getHead(),
                    sc, dc));
            PythonAnalHelpers.addRenamesForConstructs(punode, aliases, valuesAssignedToPunodeHead, dc);
        }
    }
    
}
