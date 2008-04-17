package com.yoursway.sadr.python.core.typeinferencing.constructs;

import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo;
import com.yoursway.sadr.core.constructs.DynamicContext;
import com.yoursway.sadr.python.core.typeinferencing.services.VariableLookup;

public interface PythonDynamicContext extends DynamicContext {
    
    ValueInfo selfType();
    
    VariableLookup variableLookup();
    
}
