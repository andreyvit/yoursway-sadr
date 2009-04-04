package com.yoursway.sadr.python.analysis.objectmodel.values;

import kilim.pausable;

import com.yoursway.sadr.python.analysis.aliasing.AliasConsumer;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.index.data.PassedArgumentInfo;
import com.yoursway.sadr.python.analysis.lang.unodes.Suffix;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSetBuilder;

public interface CallableObject {
    
    public abstract String name();
    
    @pausable
    public void call(PythonDynamicContext dc, PythonValueSetBuilder builder);
    
    void computeArgumentAliases(PassedArgumentInfo info, PythonDynamicContext dc, Suffix suffix,
            AliasConsumer aliases);
    
}
