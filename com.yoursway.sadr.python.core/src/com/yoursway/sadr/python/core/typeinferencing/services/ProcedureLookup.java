package com.yoursway.sadr.python.core.typeinferencing.services;

import com.yoursway.sadr.python.core.runtime.RubyProcedure;

public interface ProcedureLookup {
    
    RubyProcedure findProcedure(String name);
    
}
