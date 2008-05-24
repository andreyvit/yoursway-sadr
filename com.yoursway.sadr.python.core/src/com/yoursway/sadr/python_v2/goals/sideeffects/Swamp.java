package com.yoursway.sadr.python_v2.goals.sideeffects;

import com.yoursway.sadr.python_v2.constructs.Frog;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;

public interface Swamp {
    
    void parrotizeStartingWithParentOf(PythonConstruct currentConstruct, Frog frog, Request request);
    
    void parrotizeStartingWith(PythonConstruct previousConstruct, Frog frog, Request request);
    
}
