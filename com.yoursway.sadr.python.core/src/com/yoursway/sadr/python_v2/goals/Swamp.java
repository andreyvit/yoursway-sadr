package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python.core.typeinferencing.constructs.Frog;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;

public interface Swamp {
    
    void parrotizeStartingWithParentOf(PythonConstruct currentConstruct, Frog frog, Request request);
    
    void parrotizeStartingWith(PythonConstruct previousConstruct, Frog frog, Request request);
    
}
