package com.yoursway.sadr.python.analysis.objectmodel.valueset;

import java.util.List;

import com.yoursway.sadr.python.analysis.objectmodel.values.PythonValue;

public interface PythonValueSetBuilder {
    
    PythonValueSet build();
    
    void addResult(PythonValue result);
    
    void addResults(PythonValueSet r);
    
    void addAll(List<PythonValueSet> results);
    
}
