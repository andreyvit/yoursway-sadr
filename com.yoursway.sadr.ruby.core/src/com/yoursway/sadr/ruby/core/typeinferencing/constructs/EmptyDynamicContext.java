package com.yoursway.sadr.ruby.core.typeinferencing.constructs;

import com.yoursway.sadr.ruby.core.runtime.RubyVariable;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.ruby.core.typeinferencing.services.VariableLookup;

public class EmptyDynamicContext implements RubyDynamicContext {
    
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
