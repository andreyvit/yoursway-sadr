package com.yoursway.sadr.ruby.core.runtime;

import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyConstruct;

public interface Callable {
    
    boolean isBuiltin();
    
    String name();
    
    RubyArgument[] arguments();
    
    RubyConstruct construct();
    
    String[] parameterNames();
    
    String qualifiedName();
    
}
