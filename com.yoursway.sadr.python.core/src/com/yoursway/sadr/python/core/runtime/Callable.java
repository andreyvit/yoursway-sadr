package com.yoursway.sadr.python.core.runtime;

import com.yoursway.sadr.python.core.typeinferencing.constructs.dtl.PythonConstruct;

public interface Callable {
    
    boolean isBuiltin();
    
    String name();
    
    RubyArgument[] arguments();
    
    PythonConstruct construct();
    
    String[] parameterNames();
    
    String qualifiedName();
    
}
