package com.yoursway.sadr.ruby.core.typeinferencing.constructs;

import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.ruby.core.typeinferencing.services.VariableLookup;

public interface DynamicContext {
    
    ValueInfo selfType();
    
    VariableLookup variableLookup();
    
}
