package com.yoursway.sadr.python.core.typeinferencing.constructs;

import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.python.core.typeinferencing.services.VariableLookup;

public interface DynamicContext {
    
    ValueInfo selfType();
    
    VariableLookup variableLookup();
    
}
