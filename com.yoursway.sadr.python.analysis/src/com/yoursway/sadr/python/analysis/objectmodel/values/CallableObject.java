package com.yoursway.sadr.python.analysis.objectmodel.values;

import java.util.List;

import kilim.pausable;

import com.yoursway.sadr.python.analysis.aliasing.Alias;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.index.data.PassedArgumentInfo;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSetBuilder;

public interface CallableObject {
    
    public abstract String name();
    
    // may return null
    public PythonConstruct getDecl();
    
    @pausable
    public void call(PythonDynamicContext dc, PythonValueSetBuilder builder);
    
    void computeArgumentAliases(PassedArgumentInfo info, PythonDynamicContext dc, List<Alias> unodes);
    
}
