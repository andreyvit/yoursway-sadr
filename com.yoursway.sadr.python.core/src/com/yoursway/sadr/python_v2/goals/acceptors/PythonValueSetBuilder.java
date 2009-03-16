package com.yoursway.sadr.python_v2.goals.acceptors;

import com.yoursway.sadr.python_v2.model.builtins.PythonValue;

public interface PythonValueSetBuilder {
    
    PythonValueSet build();
    
    void addResult(PythonValue result);

    void addResults(PythonValueSet r);
    
}
