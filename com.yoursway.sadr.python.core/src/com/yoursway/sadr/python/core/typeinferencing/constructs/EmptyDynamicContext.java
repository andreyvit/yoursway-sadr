package com.yoursway.sadr.python.core.typeinferencing.constructs;

import com.yoursway.sadr.python.core.runtime.PythonVariable;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo;
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
