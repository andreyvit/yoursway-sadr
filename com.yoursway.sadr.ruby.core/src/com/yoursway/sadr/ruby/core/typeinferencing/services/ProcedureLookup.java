package com.yoursway.sadr.ruby.core.typeinferencing.services;

import com.yoursway.sadr.ruby.core.runtime.RubyProcedure;

public interface ProcedureLookup {
    
    RubyProcedure findProcedure(String name);
    
}
