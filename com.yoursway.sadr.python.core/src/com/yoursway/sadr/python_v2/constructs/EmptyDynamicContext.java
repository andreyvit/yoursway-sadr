package com.yoursway.sadr.python_v2.constructs;

import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo;
import com.yoursway.sadr.python.core.runtime.PythonVariable;
import com.yoursway.sadr.python.core.typeinferencing.services.VariableLookup;

public class EmptyDynamicContext implements PythonDynamicContext {
    
    public ValueInfo selfType() {
        return null;
    }
    
    public VariableLookup variableLookup() {
        return new VariableLookup() {
            
            public PythonVariable findVariable(String name) {
                return null;
            }
            
            public PythonVariable lookupVariable(String name) {
                return null;
            }
            
        };
    }
    
}
