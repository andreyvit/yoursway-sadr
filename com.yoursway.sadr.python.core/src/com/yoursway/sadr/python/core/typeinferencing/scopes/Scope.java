package com.yoursway.sadr.python.core.typeinferencing.scopes;

import java.util.List;

import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;

public interface Scope extends PythonConstruct {
    /**
     * @return parent scope
     */
    Scope parentScope();
    
    List<PythonConstruct> getEnclosedconstructs();
    
    String displayName();
}
