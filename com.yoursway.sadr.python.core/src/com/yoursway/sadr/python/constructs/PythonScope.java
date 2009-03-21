package com.yoursway.sadr.python.constructs;

import java.util.List;

public interface PythonScope {
    
    PythonScope parentScope();
    
    List<PythonScope> currentScopes();
    
}
