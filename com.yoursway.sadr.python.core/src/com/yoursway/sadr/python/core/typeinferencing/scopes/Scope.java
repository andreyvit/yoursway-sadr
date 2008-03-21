package com.yoursway.sadr.python.core.typeinferencing.scopes;

import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonDynamicContext;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonStaticContext;
import com.yoursway.sadr.python.core.typeinferencing.services.NodeLookup;

public interface Scope extends PythonStaticContext, PythonDynamicContext {
    
    NodeLookup nodeLookup();
    
    FileScope fileScope();
    
    PythonConstruct createConstruct();
    
}
