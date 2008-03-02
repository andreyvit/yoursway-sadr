package com.yoursway.sadr.python.core.typeinferencing.constructs.dtl;

import com.yoursway.sadr.python.core.runtime.RubyVariable;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.python.core.typeinferencing.services.VariableLookup;

public class EmptyDynamicContext implements PythonDynamicContext {
    
    public ValueInfo selfType() {
        return null;
    }
    
    public VariableLookup variableLookup() {
        return new VariableLookup() {
            
            public RubyVariable findVariable(String name) {
                return null;
            }
            
            public RubyVariable lookupVariable(String name) {
                return null;
            }
            
        };
    }
    
}
