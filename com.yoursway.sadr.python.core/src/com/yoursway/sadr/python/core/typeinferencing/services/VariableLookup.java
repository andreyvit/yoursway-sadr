package com.yoursway.sadr.python.core.typeinferencing.services;

import com.yoursway.sadr.python.core.runtime.RubyVariable;

public interface VariableLookup {
    
    RubyVariable findVariable(String name);
    
    RubyVariable lookupVariable(String name);
    
}
