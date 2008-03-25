package com.yoursway.sadr.ruby.core.typeinferencing.services;

import com.yoursway.sadr.ruby.core.runtime.RubyVariable;

public interface VariableLookup {
    
    RubyVariable findVariable(String name);
    
    RubyVariable lookupVariable(String name);
    
}
