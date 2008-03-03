package com.yoursway.sadr.python.core.typeinferencing.services;

import com.yoursway.sadr.python.core.runtime.PythonProcedure;

public interface ProcedureLookup {
    
    PythonProcedure findProcedure(String name);
    
}
