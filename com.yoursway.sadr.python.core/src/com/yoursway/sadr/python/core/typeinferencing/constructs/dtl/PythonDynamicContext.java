package com.yoursway.sadr.python.core.typeinferencing.constructs.dtl;

import com.yoursway.sadr.core.constructs.DynamicContext;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.python.core.typeinferencing.services.VariableLookup;

public interface PythonDynamicContext extends DynamicContext {
    
    ValueInfo selfType();
    
    VariableLookup variableLookup();
    
}
