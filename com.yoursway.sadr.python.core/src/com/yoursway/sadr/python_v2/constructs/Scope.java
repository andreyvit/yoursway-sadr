package com.yoursway.sadr.python_v2.constructs;

import java.util.List;


public interface Scope extends PythonConstruct {
    /**
     * @return parent scope
     */
    Scope parentScope();
    
    List<PythonConstruct> getEnclosedConstructs();
    
    List<PythonConstruct> getEnclosedConstructs2();
    
    String displayName();
    
    String name();
    
    PythonFileC getFileScope();
    
}
