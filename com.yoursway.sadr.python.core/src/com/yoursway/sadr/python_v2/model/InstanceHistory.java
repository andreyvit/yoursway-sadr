package com.yoursway.sadr.python_v2.model;

import com.yoursway.sadr.python_v2.constructs.PythonConstruct;

public interface InstanceHistory {
    /**
     * @return if history contains specified object.
     */
    boolean contains(RuntimeObject instance);
    
    /**
     * @return initially constructed instance.
     */
    RuntimeObject source();
    
    /**
     * @return a construct which declares or crates the source object.
     */
    PythonConstruct sourceDeclaration();
}
