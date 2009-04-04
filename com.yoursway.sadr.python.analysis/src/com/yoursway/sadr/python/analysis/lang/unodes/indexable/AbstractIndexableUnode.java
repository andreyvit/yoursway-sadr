package com.yoursway.sadr.python.analysis.lang.unodes.indexable;

import java.util.Collection;
import java.util.Set;

import kilim.pausable;

import com.yoursway.sadr.python.analysis.PythonAnalHelpers;
import com.yoursway.sadr.python.analysis.aliasing.Alias;
import com.yoursway.sadr.python.analysis.aliasing.AliasConsumer;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonStaticContext;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.lang.unodes.Suffix;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public abstract class AbstractIndexableUnode extends Unode {
    
    @Override
    public boolean isIndexable() {
        return true;
    }
    
    @Override
    @pausable
    public void findRenames(Suffix suffix, PythonStaticContext sc, PythonDynamicContext dc,
            AliasConsumer aliases) {
        if (isIndexable()) {
            Collection<PythonConstruct> valuesAssignedToPunodeHead = new Alias(this, sc, dc)
                    .findRenamesUsingIndex();
            PythonAnalHelpers.addRenamesForConstructs(suffix, aliases, valuesAssignedToPunodeHead, dc);
        }
    }
    
    @pausable
    protected final PythonValueSet trackAssignmentsAndRenames(PythonStaticContext sc, PythonDynamicContext dc) {
        Set<Alias> aliases = new Alias(this, sc, dc).computeAliases();
        return PythonAnalHelpers.queryIndexForValuesAssignedTo(aliases);
    }
    
}
