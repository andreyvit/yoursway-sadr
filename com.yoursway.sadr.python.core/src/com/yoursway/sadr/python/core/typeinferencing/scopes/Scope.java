package com.yoursway.sadr.python.core.typeinferencing.scopes;

import java.util.List;

import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;

public interface Scope {
    List<PythonConstruct> getEnclosedconstructs();
    
    /**
     * @return parent scope
     */
    Scope scope();
}
