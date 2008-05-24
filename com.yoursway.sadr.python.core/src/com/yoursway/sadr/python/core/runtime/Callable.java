package com.yoursway.sadr.python.core.runtime;

import com.yoursway.sadr.python_v2.constructs.PythonConstruct;

public interface Callable {
    
    boolean isBuiltin();
    
    String name();
    
    PythonCallableArgument[] arguments();
    
    PythonConstruct construct();
    
    String[] parameterNames();
    
    String qualifiedName();
    
}
