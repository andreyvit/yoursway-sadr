package com.yoursway.sadr.python.core.typeinferencing.services;

import com.yoursway.sadr.python.core.runtime.PythonVariable;

public interface VariableLookup {
    
    PythonVariable findVariable(String name);
    
    PythonVariable lookupVariable(String name);
    
}
