package com.yoursway.sadr.python.core.runtime;

import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;

public interface Callable {
    
    boolean isBuiltin();
    
    String name();
    
    PythonCallableArgument[] arguments();
    
    PythonConstruct construct();
    
    String[] parameterNames();
    
    String qualifiedName();
    
}
