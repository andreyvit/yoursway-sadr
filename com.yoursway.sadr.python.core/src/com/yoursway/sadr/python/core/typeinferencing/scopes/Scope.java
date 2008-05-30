package com.yoursway.sadr.python.core.typeinferencing.scopes;

import java.util.List;

import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.constructs.PythonFileC;

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
